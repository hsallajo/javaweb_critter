package com.shu.jdnd.course3.critter.model;

import com.shu.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
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

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    private LocalDate birthDate;

    @Column(length = 1024)
    private String notes;

    public Pet() {
    }

    public Pet(Long id, PetType petType, String name, Customer owner, LocalDate birthDate, String notes) {
        this.id = id;
        this.petType = petType;
        this.name = name;
        this.owner = owner;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Pet(PetType petType, String name, Customer owner, LocalDate birthDate, String notes) {
        this.petType = petType;
        this.name = name;
        this.owner = owner;
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

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
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
