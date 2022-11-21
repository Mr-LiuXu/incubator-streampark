package org.apache.streampark.console.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxu
 * @desc
 * @date 2022/11/18
 */

@Data
@TableName("t_kafka_alarm")
public class KafkaAlarm implements Serializable {
    private static final long serialVersionUID = -4852732617765810959L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String broker;
    private String topic;
    private String group_id;
    private int is_enable_security;
    private String user_name;
    private String user_pass;
    private Long lag_threshold;
    private int status;
    private Date create_time;
    private Date modify_time;
}
