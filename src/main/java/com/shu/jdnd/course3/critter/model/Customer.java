package com.shu.jdnd.course3.critter.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends User {

    @Column(length = 1024)
    private String notes;
    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Pet> pets;

    public Customer() {
    }

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        super(id, name);
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.pets = pets;
    }

    public Customer(String name, String phoneNumber, String notes, List<Pet> pets) {
        super(name);
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> petIds) {
        this.pets = petIds;
    }
}
