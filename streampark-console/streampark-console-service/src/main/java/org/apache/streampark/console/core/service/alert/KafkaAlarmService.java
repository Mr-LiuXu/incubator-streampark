package org.apache.streampark.console.core.service.alert;

import org.apache.streampark.console.core.entity.KafkaAlarm;

import java.util.List;

public interface KafkaAlarmService {

    List<KafkaAlarm> getKafkaAlarm();
}
