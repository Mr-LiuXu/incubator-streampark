package org.apache.streampark.console.core.kafka;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;


/**
 *
 * @Description:获取consumers中topic的消费情况
 * @Date: 2022年1月25日
 * @version
 */
public class GetKafkaLag {

    public final static String KAFKA_BOOTSTRAP_SERVERS = "60.205.171.233:9092";
  //  public final static String KAFKA_BOOTSTRAP_SERVERS = "yz-cen111.kafka.data.sina.com.cn:9110,yz-cen052.kafka.data.sina.com.cn:9110,yz-cen033.kafka.data.sina.com.cn:9110,yz-cen015.kafka.data.sina.com.cn:9110,yz-cen018.kafka.data.sina.com.cn:9110";

    public static Properties getConsumeProperties(String groupID) {
        Properties props = new Properties();
        props.put("group.id", groupID);
        props.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

  /*      String username = "sina_up";
        String password = "9fd35e861e37613d9af4fedbd5282f36";
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\"  password=\"" + password + "\";");
*/
        return props;
    }

    public static void main(String[] args) {
        // 要查询的消费组
        String groupID = "cc";
        // 消费组中监控的topic
        String topic = "mykafka_1";

        //String groupID = "sina_up_sinanewsapp_interaction_transmit_all_bhv";
  /*      String groupID = "sina_up_portrait_bhv_test";
        String topic = "sinanewsapp_interaction_transmit_all";*/
        long s = System.currentTimeMillis();
        long countLong = countByArgs(groupID, topic);
        long e = System.currentTimeMillis();
        long l = e - s;
        System.out.println("s="+s+",e="+e+",chazhi="+l);

        System.out.println("countLong="+countLong);

    }

    public static long countByArgs(String groupID, String topic) {
        Map<Integer, Long> endOffsetMap = new HashMap<Integer, Long>();
        Map<Integer, Long> commitOffsetMap = new HashMap<Integer, Long>();

        Properties consumeProps = getConsumeProperties(groupID);
        System.out.println("consumer properties:" + consumeProps);
        // 查询topic partitions
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumeProps);
        List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
        List<PartitionInfo> partitionsFor = consumer.partitionsFor(topic);
        for (PartitionInfo partitionInfo : partitionsFor) {
            TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
            topicPartitions.add(topicPartition);
        }

        // 查询log size
        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(topicPartitions);
        for (TopicPartition partitionInfo : endOffsets.keySet()) {
            endOffsetMap.put(partitionInfo.partition(), endOffsets.get(partitionInfo));
        }
        for (Integer partitionId : endOffsetMap.keySet()) {
            System.out.println(String.format("at %s, topic:%s, partition:%s, logSize:%s", System.currentTimeMillis(),
                    topic, partitionId, endOffsetMap.get(partitionId)));
        }

        // 查询消费offset
        for (TopicPartition topicAndPartition : topicPartitions) {
            OffsetAndMetadata committed = consumer.committed(topicAndPartition);
            if (committed!=null){
                commitOffsetMap.put(topicAndPartition.partition(), committed.offset());
            }else{
                System.out.println(topicAndPartition.toString());
            }
        }

        // 累加lag
        long lagSum = 0l;
        if (endOffsetMap.size() == commitOffsetMap.size()) {
            for (Integer partition : endOffsetMap.keySet()) {
                long endOffSet = endOffsetMap.get(partition);
                long commitOffSet = commitOffsetMap.get(partition);
                long diffOffset = endOffSet - commitOffSet;
                lagSum += diffOffset;
                System.out.println("Topic:" + topic + ", groupID:" + groupID + ", partition:" + partition
                        + ", endOffset:" + endOffSet + ", commitOffset:" + commitOffSet + ", diffOffset:" + diffOffset);
            }
            System.out.println("Topic:" + topic + ", groupID:" + groupID + ", LAG:" + lagSum);
        } else {
            System.out.println("this topic partitions lost");
        }

        consumer.close();

        return lagSum;
    }
}

