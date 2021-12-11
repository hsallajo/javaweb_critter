package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.model.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ScheduleRepository {

    private static String FIND_ALL_SCHEDULES = "select s from Schedule s ";


    @PersistenceContext
    EntityManager entityManager;

    //@SuppressWarnings("unchecked")
    public List<Schedule> getAllSchedules() {
        return entityManager.createQuery(FIND_ALL_SCHEDULES, Schedule.class).getResultList();
    }

    public Schedule saveSchedule(Schedule schedule){
        List<Employee> employeeRefList = new ArrayList<>();
        for (Employee e: schedule.getEmployeeList()
             ) {
            Employee ref = entityManager.getReference(Employee.class, e.getId());
            if(ref != null)
                employeeRefList.add(ref);
        }

        List<Pet> petRefList = new ArrayList<>();
        for (Pet p: schedule.getPetList()
             ) {
            Pet ref = entityManager.getReference(Pet.class, p.getId());
            if(ref != null)
                petRefList.add(ref);
        }

        schedule.setEmployeeList(employeeRefList);
        schedule.setPetList(petRefList);

        return entityManager.merge(schedule);
    }


}
