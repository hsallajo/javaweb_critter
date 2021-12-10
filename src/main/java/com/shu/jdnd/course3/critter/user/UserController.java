package com.shu.jdnd.course3.critter.user;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.service.CustomerService;
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
    private CustomerService customerService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getName().equals("") || customerDTO.getPhoneNumber().equals(""))
            throw new IllegalArgumentException("Invalid name/phone number value.");

        customerDTO.setId(customerService.saveCustomer(customerDTOtoCustomer(customerDTO)));
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<Customer> customers = customerService.getAllCustomers();
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

        return customerToCustomerDTO(customerService.getCustomerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
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

}
