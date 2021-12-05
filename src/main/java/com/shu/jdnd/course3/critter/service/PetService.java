package com.shu.jdnd.course3.critter.service;

import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.pet.PetDTO;
import com.shu.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    @Autowired
    private CustomerService customerService;

    public PetDTO savePet(PetDTO petDTO){
        System.out.println("PetDTO arguments: name: " + petDTO.getName()
        + "\nbirth date: " + petDTO.getBirthDate()
        + "\nowner id: " + petDTO.getOwnerId()
        + "\ntype: " + petDTO.getType());

        if ( petDTO.getName().equals("")
                || petDTO.getBirthDate() == null
                || petDTO.getOwnerId() == 0
                || petDTO.getType() == null)
            throw new IllegalArgumentException("Illegal argument in PetDTO");

        if(customerService.addPet(petDTO.getOwnerId(), petDTO)) {
            return petToPetDTO(repository.save(petDTOtoPet(petDTO)));
        }
        return petDTO;
    }

    public PetDTO getPet(Long petId){
        Optional<Pet> o = repository.findById(petId);
        if(o.isEmpty())
            return new PetDTO();
        return petToPetDTO(o.get());
    }

    private Pet petDTOtoPet(PetDTO petDTO){
        return new Pet(petDTO.getId(), petDTO.getType(), petDTO.getName(), petDTO.getOwnerId(), petDTO.getBirthDate(), petDTO.getNotes());
    }

    private PetDTO petToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getOwnerId());
        return petDTO;
    }
}
