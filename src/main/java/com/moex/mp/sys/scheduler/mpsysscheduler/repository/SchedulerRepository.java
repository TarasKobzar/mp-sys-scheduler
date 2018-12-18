package com.moex.mp.sys.scheduler.mpsysscheduler.repository;

import com.moex.mp.sys.scheduler.mpsysscheduler.model.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchedulerRepository extends CrudRepository<Schedule, Long> {
    public Optional<Schedule> findTopByOrderByIdDesc();
}
