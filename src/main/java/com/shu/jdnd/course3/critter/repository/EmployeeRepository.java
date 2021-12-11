package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySkillsInAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek day);
}
