package ru.netology.daohibernate.controller;


import ru.netology.daohibernate.entity.Customer;
import ru.netology.daohibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Поиск по имени (вместо города, так как в таблице нет поля city)
    @GetMapping("/by-name")
    public ResponseEntity<List<Customer>> getCustomersByName(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.getCustomersByName(name.trim());
        return ResponseEntity.ok(customers);
    }

    // Поиск по фамилии
    @GetMapping("/by-surname")
    public ResponseEntity<List<Customer>> getCustomersBySurname(@RequestParam String surname) {
        if (surname == null || surname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.getCustomersBySurname(surname.trim());
        return ResponseEntity.ok(customers);
    }

    // Получение всех клиентов
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    // Создание нового клиента
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    // Получение клиента по ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}