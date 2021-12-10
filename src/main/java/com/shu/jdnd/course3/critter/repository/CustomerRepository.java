package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository {
    @PersistenceContext
    EntityManager entityManager;

    public void persist(Customer customer){
        entityManager.persist(customer);
    }

    public Customer find(Long id){
        return entityManager.find(Customer.class, id);
    }

    public Customer merge(Customer customer){
        return entityManager.merge(customer);
    }

    public void delete(Long id){
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.remove(customer);
    }

    @SuppressWarnings("unchecked")
    public List<Customer> getAll() {
        return entityManager.createQuery("select c from Customer c left join fetch c.pets").getResultList();
    }
}
