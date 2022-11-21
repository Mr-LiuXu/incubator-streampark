package org.apache.streampark.console.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.streampark.console.core.entity.KafkaAlarm;

import java.util.List;

public interface KafkaAlarmMapper extends BaseMapper<KafkaAlarm> {

    List<KafkaAlarm> getKafkaAlarm();


}
