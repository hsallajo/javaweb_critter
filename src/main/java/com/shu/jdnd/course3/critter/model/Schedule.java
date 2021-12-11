package com.shu.jdnd.course3.critter.model;

import com.shu.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue()
    Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Employee> employeeList;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Pet> petList;

    @Column(nullable = false)
    private LocalDate date;

    @ElementCollection
    @Column(nullable = false)
    private Set<EmployeeSkill> activities;

    public Schedule() {
    }

    public Schedule(Long scheduleId, List<Employee> employeeList, List<Pet> petList, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = scheduleId;
        this.employeeList = employeeList;
        this.petList = petList;
        this.date = date;
        this.activities = activities;
    }

    public Schedule(List<Employee> employeeList, List<Pet> petList, LocalDate date, Set<EmployeeSkill> activities) {
        this.employeeList = employeeList;
        this.petList = petList;
        this.date = date;
        this.activities = activities;
    }

    public Long getScheduleId() {
        return id;
    }

    public void setScheduleId(Long scheduleId) {
        this.id = scheduleId;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
