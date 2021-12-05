package com.shu.jdnd.course3.critter.model;

import com.shu.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private PetType petType;

    @Column(nullable = false)
    @Nationalized
    private String name;

    @Column(nullable = false)
    private Long ownerId;
    private LocalDate birthDate;

    @Column(length = 1024)
    private String notes;

    public Pet() {
    }

    public Pet(Long id, PetType petType, String name, Long ownerId, LocalDate birthDate, String notes) {
        this.id = id;
        this.petType = petType;
        this.name = name;
        this.ownerId = ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Pet(PetType petType, String name, Long ownerId, LocalDate birthDate, String notes) {
        this.petType = petType;
        this.name = name;
        this.ownerId = ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
