package ru.netology.daohibernate.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import ru.netology.daohibernate.entity.Customer;
import ru.netology.daohibernate.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Поиск по городу
    @GetMapping("/by-city")
    public ResponseEntity<List<Customer>> getCustomersByCity(@RequestParam String city) {
        if (city == null || city.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.findByCity(city.trim());
        return ResponseEntity.ok(customers);
    }

    // Поиск по возрасту (меньше указанного, отсортировано по возрастанию)
    @GetMapping("/by-age-less-than")
    public ResponseEntity<List<Customer>> getCustomersByAgeLessThan(@RequestParam Integer age) {
        if (age == null || age < 0) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.findByAgeLessThanOrderByAgeAsc(age);
        return ResponseEntity.ok(customers);
    }

    // Поиск по имени и фамилии (возвращает Optional)
    @GetMapping("/by-name-surname")
    public ResponseEntity<Customer> getCustomerByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname) {
        if (name == null || name.trim().isEmpty() || surname == null || surname.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Customer> customer = customerRepository.findByNameAndSurname(name.trim(), surname.trim());
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Поиск по имени
    @GetMapping("/by-name")
    public ResponseEntity<List<Customer>> getCustomersByName(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.findByNameIgnoreCase(name.trim());
        return ResponseEntity.ok(customers);
    }

    // Поиск по фамилии
    @GetMapping("/by-surname")
    public ResponseEntity<List<Customer>> getCustomersBySurname(@RequestParam String surname) {
        if (surname == null || surname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.findBySurnameIgnoreCase(surname.trim());
        return ResponseEntity.ok(customers);
    }

    // Поиск по точному возрасту
    @GetMapping("/by-age")
    public ResponseEntity<List<Customer>> getCustomersByAge(@RequestParam Integer age) {
        if (age == null || age < 0) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Customer> customers = customerRepository.findByAge(age);
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
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Обновление клиента
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(customerDetails.getName());
            customer.setSurname(customerDetails.getSurname());
            customer.setAge(customerDetails.getAge());
            customer.setCity(customerDetails.getCity());
            customer.setPhoneNumber(customerDetails.getPhoneNumber());

            Customer updatedCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удаление клиента
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}