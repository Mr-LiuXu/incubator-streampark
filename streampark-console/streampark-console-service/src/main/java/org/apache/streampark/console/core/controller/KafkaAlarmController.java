package org.apache.streampark.console.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.streampark.console.base.domain.RestResponse;
import org.apache.streampark.console.core.entity.KafkaAlarm;
import org.apache.streampark.console.core.service.alert.KafkaAlarmService;
import org.apache.streampark.console.system.entity.User;
import org.apache.streampark.console.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuxu
 * @desc
 * @date 2022/11/21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("kafka/alarm")
public class KafkaAlarmController {

    @Autowired
    private KafkaAlarmService kafkaAlarmService;

    @PostMapping("getKafkaAlarm")
    public RestResponse getKafkaAlarm() {
        final List<KafkaAlarm> kafkaAlarm = this.kafkaAlarmService.getKafkaAlarm();
        return RestResponse.success(kafkaAlarm);
    }

}
