package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.model.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ScheduleRepository {

    private static String FIND_ALL_SCHEDULES = "select s from Schedule s ";
    private static String FIND_SCHEDULES_FOR_EMPLOYEE = "select s from Schedule s where :e member of s.employeeList";


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

    public List<Schedule> findSchedulesForEmployee(Long id){
        TypedQuery<Schedule> q = entityManager.createQuery(FIND_SCHEDULES_FOR_EMPLOYEE, Schedule.class);
        Employee employee = entityManager.getReference(Employee.class, id);
        q.setParameter("e", employee);
        return q.getResultList();
    };


}
