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
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        if(employee.isPresent())
            return employee.get();
        return null;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional o = employeeRepository.findById(employeeId);
        if(o.isPresent()){
            Employee e = (Employee) o.get();
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        }
    }

    public void setSkills(Set<EmployeeSkill> skills, long employeeId) {
        Optional o = employeeRepository.findById(employeeId);
        if(o.isPresent()){
            Employee e = (Employee) o.get();
            e.setSkills(skills);
            employeeRepository.save(e);
        }
    }
}
