package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.exception.CritterAPIRequestException;
import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ScheduleRepository {

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    private static final String FIND_ALL_SCHEDULES = "select s from Schedule s ";
    private static final String FIND_SCHEDULES_FOR_EMPLOYEE = "select s from Schedule s where :employee member of s.employeeList";
    private static final String FIND_SCHEDULES_FOR_PET = "select s from Schedule s where :pet member of s.petList";

    @PersistenceContext
    EntityManager entityManager;

    public List<Schedule> getAllSchedules() {
        return entityManager.createQuery(FIND_ALL_SCHEDULES, Schedule.class).getResultList();
    }

    public Schedule saveSchedule(Schedule schedule){
        List<Employee> employeeRefList = new ArrayList<>();
        for (Employee e: schedule.getEmployeeList()
             ) {
            Optional<Employee> employee = employeeRepository.findById(e.getId());
            if(employee.isPresent())
                employeeRefList.add(entityManager.getReference(Employee.class, e.getId()));
            else
                throw new CritterAPIRequestException("Critter API exception: Employee not found in database");
        }

        List<Pet> petRefList = new ArrayList<>();
        for (Pet p: schedule.getPetList()
             ) {
            Optional<Pet> pet = petRepository.findById(p.getId());
            if(pet.isPresent())
                petRefList.add(entityManager.getReference(Pet.class, p.getId()));
            else
                throw new CritterAPIRequestException("Critter API exception: Pet not found in database");

        }

        schedule.setEmployeeList(employeeRefList);
        schedule.setPetList(petRefList);

        return entityManager.merge(schedule);
    }

    public List<Schedule> findSchedulesForEmployee(Long id){
        TypedQuery<Schedule> q = entityManager.createQuery(FIND_SCHEDULES_FOR_EMPLOYEE, Schedule.class);
        Employee employee = entityManager.getReference(Employee.class, id);
        q.setParameter("employee", employee);
        return q.getResultList();
    }

    public List<Schedule> findSchedulesForPet(long petId) {
        TypedQuery<Schedule> q = entityManager.createQuery(FIND_SCHEDULES_FOR_PET, Schedule.class);
        Pet pet = entityManager.getReference(Pet.class, petId);
        q.setParameter("pet", pet);
        return q.getResultList();
    }


    public List<Schedule> findSchedulesForCustomer(long customerId) {
        Customer owner = entityManager.getReference(Customer.class, customerId);
        List<Pet> pets = petRepository.findAllByOwner(owner);

        List<Schedule> schedules = new ArrayList<>();
        for (Pet pet: pets
             ) {
            TypedQuery<Schedule> q = entityManager.createQuery(FIND_SCHEDULES_FOR_PET, Schedule.class);
            q.setParameter("pet", pet);
            List<Schedule> res = q.getResultList();
            if(res.size() > 0)
                schedules.addAll(res);
        }

        return schedules;
    }
}
