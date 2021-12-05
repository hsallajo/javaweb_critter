package com.shu.jdnd.course3.critter.service;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.pet.PetDTO;
import com.shu.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer createCustomer(Customer customer){
        return repository.save(customer);
    }

    public boolean addPet(Long customerId, PetDTO pet){

        if(customerId == 0 || pet == null)
            throw new IllegalArgumentException("customer id, petDTO must have non-zero values");

        Optional<Customer> o = repository.findById(customerId);
        Customer c;
        if (o.isPresent()){
            c = o.get();
            List<Long> updatedList = c.getPetIds();
            if(updatedList == null)
                updatedList = new ArrayList<>();
            updatedList.add(pet.getId());
            c.setPetIds(updatedList);
            repository.save(c);
            return true;
        }
        return false;
    }
}
