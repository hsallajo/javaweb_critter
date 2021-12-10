package com.shu.jdnd.course3.critter.repository;

import com.shu.jdnd.course3.critter.model.Customer;
import com.shu.jdnd.course3.critter.model.Pet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository {

    private static final String FIND_OWNER_BY_PET = "select c from Customer c left join fetch c.pets where :pet member of c.pets";
    private static final String FIND_ALL_CUSTOMERS = "select c from Customer c left join fetch c.pets";

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
    public List<Customer> getAllCustomers() {
        return entityManager.createQuery(FIND_ALL_CUSTOMERS).getResultList();
    }

    public Customer findCustomerByPet(Long petId) {
        Pet p = entityManager.find(Pet.class, petId);
        TypedQuery<Customer> q = entityManager.createQuery(FIND_OWNER_BY_PET, Customer.class);
        q.setParameter("pet", p);
        return q.getResultList().get(0);
    }
}
