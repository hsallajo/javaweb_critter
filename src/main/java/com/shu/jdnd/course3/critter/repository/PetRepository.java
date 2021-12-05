package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
