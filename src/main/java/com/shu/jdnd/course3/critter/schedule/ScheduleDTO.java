package com.shu.jdnd.course3.critter.schedule;

import com.shu.jdnd.course3.critter.user.EmployeeSkill;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<java.lang.Long> employeeIds;
    private List<java.lang.Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public List<java.lang.Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<java.lang.Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<java.lang.Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<java.lang.Long> petIds) {
        this.petIds = petIds;
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
