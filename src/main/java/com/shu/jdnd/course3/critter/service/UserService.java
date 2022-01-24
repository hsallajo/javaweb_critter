package com.shu.jdnd.course3.critter.service;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.repository.CustomerRepository;
import com.shu.jdnd.course3.critter.repository.EmployeeRepository;
import com.shu.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class UserService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Long saveCustomer(Customer customer){
        customer.getPets().forEach(pet -> pet.setOwner(customer));
        repository.persist(customer);
        return customer.getId();
    }

    public List<Customer> getAllCustomers(){
        return repository.getAllCustomers();
    }

    public Customer getCustomer(Long id){
        return repository.find(id);
    }

    public Customer getCustomerByPetId(Long id) {
        return repository.findCustomerByPet(id);
    }

    public Long saveEmployee(Employee employee){
        return employeeRepository.save(employee).getId();
    }

    public Employee getEmployee(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> o = employeeRepository.findById(employeeId);
        if(o.isPresent()){
            Employee e = o.get();
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        }
    }

    public void setSkills(Set<EmployeeSkill> skills, long employeeId) {
        Optional<Employee> o = employeeRepository.findById(employeeId);
        if(o.isPresent()){
            Employee e = o.get();
            e.setSkills(skills);
            employeeRepository.save(e);
        }
    }

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {

        List<Employee> employees = new ArrayList<>();
        int requiredSkillsCnt = skills.size();

        List<Employee> res = employeeRepository.findBySkillsInAndDaysAvailable(skills, date.getDayOfWeek());

        /*remove employees who do not have all required skills*/

        for (Employee e: res
             ) {
            int matches = 0;
            for (EmployeeSkill s: skills
                 ) {
                if(!e.getSkills().contains(s))
                    break;
                matches++;
            }
            if(matches == requiredSkillsCnt) {
                if(!employees.contains(e))
                    employees.add(e);
            }

        }
        return employees;
    }
}
