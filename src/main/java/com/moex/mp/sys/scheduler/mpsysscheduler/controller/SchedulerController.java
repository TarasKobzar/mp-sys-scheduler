package com.moex.mp.sys.scheduler.mpsysscheduler.controller;

import com.moex.mp.sys.scheduler.mpsysscheduler.model.Schedule;
import com.moex.mp.sys.scheduler.mpsysscheduler.repository.SchedulerRepository;
import com.moex.mp.sys.scheduler.mpsysscheduler.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@Api(value = "Scheduler", description = "The scheduler API")
public class SchedulerController {

    private SchedulerService schedulerService;
    private SchedulerRepository schedulerRepository;

    @Autowired
    public SchedulerController(SchedulerService schedulerService, SchedulerRepository schedulerRepository) {
        this.schedulerService = schedulerService;
        this.schedulerRepository = schedulerRepository;
    }

    @PostMapping("/schedule")
    @ApiOperation(value = "Set a new schedule for requests", nickname = "SchedulePost", response = Schedule.class)
    @ApiResponse(code = 201, message = "Successful operation", response = Schedule.class)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Schedule setNewRequestSchedule(
            @ApiParam(value = "New request schedule", required = true) @RequestBody Schedule schedule){
        return schedulerService.saveSchedule(schedule);
    }

    @GetMapping("/schedule")
    @ApiOperation(value = "Get the latest schedule", nickname = "ScheduleGet", response = Schedule.class)
    @ApiResponse(code = 200, message = "Successful operation", response = Schedule.class)
    public ResponseEntity getTheLatest(){
        Optional<Schedule> entry = schedulerRepository.findTopByOrderByIdDesc();
        return entry.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().eTag("No schedule change record").build());
    }
}
