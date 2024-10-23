package com.eteration.simplebanking.services;


import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// This class is a place holder you can change the complete implementation
@Service
public class AccountService {
    private final Map<String, Account> accounts = new HashMap<>();

    public void createAccount(String owner, String accountNumber) {
        accounts.put(accountNumber, new Account(owner, accountNumber));
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = accounts.get(accountNumber);
        account.withdraw(amount);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}
