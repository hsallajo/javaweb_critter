package com.shu.jdnd.course3.critter.model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Customer extends User {

    @Column(length = 1024)
    private String notes;
    @Column(nullable = false)
    private String phoneNumber;

    @ElementCollection
    private List<Long> petIds;

    public Customer() {
    }

    public Customer(Long id, String name, String phoneNumber, String notes, List<Long> petIds) {
        super(id, name);
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.petIds = petIds;
    }

    public Customer(String name, String phoneNumber, String notes, List<Long> petIds) {
        super(name);
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.petIds = petIds;
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

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}
