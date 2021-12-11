package com.shu.jdnd.course3.critter.service;

import com.shu.jdnd.course3.critter.model.Schedule;
import com.shu.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.getAllSchedules();
    }

    public Schedule saveSchedule(Schedule schedule){
        return scheduleRepository.saveSchedule(schedule);
    }

    public List<Schedule> findSchedulesForEmployee(Long id){
        return scheduleRepository.findSchedulesForEmployee(id);
    }
}
