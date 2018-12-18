package com.moex.mp.sys.scheduler.mpsysscheduler.service;

import com.moex.mp.sys.scheduler.mpsysscheduler.exception.AddressForMessageException;
import com.moex.mp.sys.scheduler.mpsysscheduler.exception.CronExpressionIsInvalidException;
import com.moex.mp.sys.scheduler.mpsysscheduler.exception.UriSyntaxCustomException;
import com.moex.mp.sys.scheduler.mpsysscheduler.model.Schedule;
import com.moex.mp.sys.scheduler.mpsysscheduler.repository.SchedulerRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.springframework.scheduling.support.CronSequenceGenerator.isValidExpression;

@Service
public class SchedulerService implements SchedulingConfigurer {

    private Schedule schedule = new Schedule();
    private SchedulerRepository schedulerRepository;
    private RestTemplate restTemplate;
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Value("${schedule.topicName}")
    private String topicName;

    @Value("${schedule.cronExpression}")
    private String cronExpression;

    @Value("${schedule.customRequest}")
    private String customRequest;

    @Autowired
    public SchedulerService(SchedulerRepository schedulerRepository, KafkaTemplate<String, byte[]> kafkaTemplate, RestTemplate restTemplate) {
        this.schedulerRepository = schedulerRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void init(){
        schedule.setTopicName(topicName);
        schedule.setCronExpression(cronExpression);
        schedule.setCustomRequest(customRequest.getBytes());
    }

    public Schedule saveSchedule(Schedule schedule) {
        if (isValidExpression(schedule.getCronExpression())){
            if (schedule.getTopicName() != schedule.getUrlAddress() &&
                    (schedule.getTopicName() == null || schedule.getUrlAddress() == null)){
                this.schedule = schedule;
                return schedulerRepository.save(schedule);
            }
            else {
                throw new AddressForMessageException();
            }
        }
        else {
            throw new CronExpressionIsInvalidException();
        }
    }

    private void sendingMessage() throws URISyntaxException {
        if (schedule.getTopicName() != null){
            kafkaTemplate.send(schedule.getTopicName(), schedule.getCustomRequest());
        }
        else {
            restTemplate.postForLocation(new URI(schedule.getUrlAddress()), schedule.getCustomRequest());
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(() -> {
                    try {
                        sendingMessage();
                    } catch (URISyntaxException e) {
                        // to do logging
                        throw new UriSyntaxCustomException();
                    }
                },
                (triggerContext) -> {
                    CronTrigger trigger = new CronTrigger(schedule.getCronExpression());
                    return trigger.nextExecutionTime(triggerContext);
                });
    }

    // for checking kafka listener
    @KafkaListener(topics = "time-topic")
    public void listen(ConsumerRecord<?, String> consumerRecord){
        System.out.println(consumerRecord);
        System.out.println(Arrays.toString(consumerRecord.value().getBytes()));
    }
}
