package ru.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.boot_security.demo.model.Bank;
import ru.spring.boot_security.demo.repository.BankRepository;

@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank getBankById(){
        return bankRepository.findById(1L).get();
    }

    public void updateBank(Bank bank,int amount){
        bank.setScore((float) (bank.getScore() + amount * 0.1));
        bankRepository.save(bank);
    }
}
