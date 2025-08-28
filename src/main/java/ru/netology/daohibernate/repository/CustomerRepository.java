package ru.netology.daohibernate.repository;

import ru.netology.daohibernate.entity.Customer;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Поскольку в таблице нет поля "city", будем искать по имени
    public List<Customer> getCustomersByName(String name) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name)", Customer.class);
        query.setParameter("name", name);

        return query.getResultList();
    }

    // Альтернативный метод для поиска по фамилии
    public List<Customer> getCustomersBySurname(String surname) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE LOWER(c.surname) = LOWER(:surname)", Customer.class);
        query.setParameter("surname", surname);

        return query.getResultList();
    }

    // Метод для получения всех клиентов
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    // Метод для сохранения клиента
    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    // Метод для поиска по ID
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }
}