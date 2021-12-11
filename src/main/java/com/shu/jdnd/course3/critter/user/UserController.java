package com.shu.jdnd.course3.critter.user;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getName().equals("") || customerDTO.getPhoneNumber().equals(""))
            throw new IllegalArgumentException("Invalid name/phone number value.");

        customerDTO.setId(userService.saveCustomer(customerDTOtoCustomer(customerDTO)));
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<Customer> customers = userService.getAllCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer c: customers
             ) {
            customerDTOs.add(customerToCustomerDTO(c));
        }
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        if(petId == 0)
            throw new IllegalArgumentException("Invalid pet id value");

        return customerToCustomerDTO(userService.getCustomerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        employeeDTO.setId(userService.saveEmployee(employeeDTOtoEmployee(employeeDTO)));
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        if(employeeId == 0)
            throw new IllegalArgumentException("Invalid employeeId");
        return employeeToEmployeeDTO(userService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setAvailability(daysAvailable, employeeId);
    }

    @PutMapping("/employee/skills/{employeeId}")
    public void setSkills(@RequestBody Set<EmployeeSkill> skills, @PathVariable long employeeId) {
        userService.setSkills(skills, employeeId);
    }


    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO requestDTO) {
        if(requestDTO.getDate() == null)
            throw new IllegalArgumentException("Invalid date param");
        if(requestDTO.getSkills() == null || requestDTO.getSkills().size() == 0)
            throw new IllegalArgumentException("Invalid employee skills param");

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> res = userService.findEmployeesForService(requestDTO.getDate(), requestDTO.getSkills());

        for (Employee e : res
             ) {
            employeeDTOList.add(employeeToEmployeeDTO(e));
        }
        return employeeDTOList;
    }

    private Customer customerDTOtoCustomer(CustomerDTO customerDTO){
        Customer c = new Customer();
        BeanUtils.copyProperties(customerDTO, c);
        c.setPets(new ArrayList<>());
        if(customerDTO.getId() == 0)
            c.setId(null);
        return c;
    }
    private CustomerDTO customerToCustomerDTO(Customer customer){
        CustomerDTO c = new CustomerDTO();
        BeanUtils.copyProperties(customer, c);
        ArrayList<Long> petIds = new ArrayList<>();
        for (Pet p: customer.getPets()
             ) {
            petIds.add(p.getId());
        }
        c.setPetIds(petIds);
        return c;
    }

    private Employee employeeDTOtoEmployee(EmployeeDTO employeeDTO) {
        Employee e = new Employee();
        BeanUtils.copyProperties(employeeDTO, e);
        if (employeeDTO.getId()==0)
            e.setId(null);
        return e;
    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee){
        EmployeeDTO e = new EmployeeDTO();
        BeanUtils.copyProperties(employee, e);
        return e;
    }

}
