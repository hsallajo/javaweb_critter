package com.shu.jdnd.course3.critter.model;

import com.shu.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User{

    @ElementCollection
    @Column(nullable = false)
    private Set<DayOfWeek> daysAvailable;
    @ElementCollection
    @Column(nullable = false)
    private Set<EmployeeSkill> skills;

    public Employee() {
    }

    public Employee(Long id, String name, Set<DayOfWeek> daysAvailable, Set<EmployeeSkill> skills) {
        super(id, name);
        this.daysAvailable = daysAvailable;
        this.skills = skills;
    }

    public Employee(String name, Set<DayOfWeek> daysAvailable, Set<EmployeeSkill> skills) {
        super(name);
        this.daysAvailable = daysAvailable;
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
}
