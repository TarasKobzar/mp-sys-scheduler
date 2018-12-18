package com.moex.mp.sys.scheduler.mpsysscheduler.model;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;

@Entity
@Table(name = "schedules")
@ApiModel(value = "ScheduleModel", description = "Schedule model for the documentation")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String topicName;

    String urlAddress;

    String cronExpression;

    byte[] customRequest;
}
