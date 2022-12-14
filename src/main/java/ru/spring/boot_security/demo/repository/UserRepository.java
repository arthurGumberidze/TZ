package ru.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(String username);
    User findByUsername(String username);
}
