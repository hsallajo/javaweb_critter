package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
