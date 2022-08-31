package ru.spring.boot_security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.boot_security.demo.model.Bank;

public interface BankRepository extends CrudRepository<Bank,Long> {
}
