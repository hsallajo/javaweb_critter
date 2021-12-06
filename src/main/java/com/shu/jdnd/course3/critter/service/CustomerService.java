package com.shu.jdnd.course3.critter.service;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Long saveCustomer(Customer customer){
        customer.getPets().forEach(pet -> pet.setOwner(customer));
        repository.persist(customer);
        return customer.getId();
    }

    public List<Customer> getAllCustomers(){
        return repository.getAll();
    }
}
