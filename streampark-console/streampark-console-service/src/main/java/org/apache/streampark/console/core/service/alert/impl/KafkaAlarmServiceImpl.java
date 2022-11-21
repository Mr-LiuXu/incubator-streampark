package org.apache.streampark.console.core.service.alert.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.streampark.console.core.entity.KafkaAlarm;
import org.apache.streampark.console.core.mapper.KafkaAlarmMapper;
import org.apache.streampark.console.core.service.alert.KafkaAlarmService;
import org.apache.streampark.console.system.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuxu
 * @desc
 * @date 2022/11/21
 */

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KafkaAlarmServiceImpl extends ServiceImpl<KafkaAlarmMapper, KafkaAlarm> implements KafkaAlarmService {

    @Override
    public List<KafkaAlarm> getKafkaAlarm() {
        System.out.println("调用===getKafkaAlarm =====");
        List<KafkaAlarm> kafkaAlarm = this.baseMapper.getKafkaAlarm();
        return kafkaAlarm;
    }
}
