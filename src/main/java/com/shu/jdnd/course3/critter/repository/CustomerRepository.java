package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
