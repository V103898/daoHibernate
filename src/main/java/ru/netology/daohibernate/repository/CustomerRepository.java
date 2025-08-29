package ru.netology.daohibernate.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.daohibernate.entity.Customer;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    // Поиск по имени
    List<Customer> findByName(String name);

    // Поиск по имени (без учета регистра)
    List<Customer> findByNameIgnoreCase(String name);

    // Поиск по фамилии
    List<Customer> findBySurname(String surname);

    // Поиск по фамилии (без учета регистра)
    List<Customer> findBySurnameIgnoreCase(String surname);

    // Метод для поиска по городу (city)
    @Query("SELECT c FROM Customer c WHERE LOWER(c.city) = LOWER(:city)")
    List<Customer> findByCity(@Param("city") String city);

    // Метод для поиска по возрасту (меньше переданного, отсортировано по возрастанию)
    @Query("SELECT c FROM Customer c WHERE c.age < :age ORDER BY c.age ASC")
    List<Customer> findByAgeLessThanOrderByAgeAsc(@Param("age") Integer age);

    // Метод для поиска по имени и фамилии (возвращает Optional)
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name) AND LOWER(c.surname) = LOWER(:surname)")
    Optional<Customer> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    // Дополнительные методы
    List<Customer> findByAge(Integer age);

    List<Customer> findByAgeGreaterThan(Integer age);

    List<Customer> findByAgeBetween(Integer minAge, Integer maxAge);

    List<Customer> findByPhoneNumber(String phoneNumber);

    // Поиск по имени с LIKE
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContaining(@Param("name") String name);
}
