package com.shu.jdnd.course3.critter.service;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.repository.CustomerRepository;
import com.shu.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(Long ownerId, Pet pet){
        Customer c = customerRepository.find(ownerId);
        if(c != null) {
            pet.setOwner(c);
            return repository.save(pet);
        }
        throw new IllegalArgumentException("Pet requires valid owner id");
    }

    public Pet getPet(Long petId){
        Optional<Pet> p = repository.findById(petId);
        if(p.isEmpty())
            return new Pet();
        return p.get();
    }

    public List<Pet> getPets(Long ownerId){
        Customer c = customerRepository.find(ownerId);
        if(c != null){
            return repository.findAllByOwner(c);
        }

        return null;
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }
}
