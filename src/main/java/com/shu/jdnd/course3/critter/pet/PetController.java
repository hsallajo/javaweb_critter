package com.shu.jdnd.course3.critter.pet;

import com.shu.jdnd.course3.critter.exception.CritterAPIRequestException;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        if ( petDTO.getName().equals("")
                || petDTO.getBirthDate() == null
                || petDTO.getOwnerId() == 0
                || petDTO.getType() == null)
            throw new CritterAPIRequestException("Illegal argument in PetDTO");

        return petToPetDTO(petService.savePet(petDTO.getOwnerId(), petDTOtoPet(petDTO)));
    }

    @PostMapping("/{ownerId}")
    public PetDTO savePetWithPath(@PathVariable long ownerId, @RequestBody PetDTO petDTO) {

        if ( petDTO.getName() == null
                || petDTO.getName().equals("")
                || petDTO.getBirthDate() == null
                || petDTO.getType() == null)
            throw new CritterAPIRequestException("Invalid API param");

        if( ownerId == 0)
            throw new CritterAPIRequestException("Invalid ownerId param");

        return petToPetDTO(petService.savePet(ownerId, petDTOtoPet(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petsToPetDTOs(petService.getAllPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        if (ownerId == 0)
            throw new CritterAPIRequestException("Illegal argument: Owner id required");
        List<Pet> pets = petService.getPets(ownerId);
        return petsToPetDTOs(pets);
    }

    private Pet petDTOtoPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setPetType(petDTO.getType());
        return pet;
    }

    private PetDTO petToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private List<PetDTO> petsToPetDTOs(List<Pet> pets){
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet : pets) {
            petDTOs.add(petToPetDTO(pet));
        }
        return petDTOs;
    }
}
