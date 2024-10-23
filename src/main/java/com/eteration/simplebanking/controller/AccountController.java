package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.TransactionStatus;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/v1")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionStatus> createAccount(@RequestParam String owner, @RequestParam String accountNumber) {
        accountService.createAccount(owner, accountNumber);
        return ResponseEntity.ok(new TransactionStatus("OK", "Account created successfully!"));
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody Map<String, Double> request) {
        double amount = request.get("amount");
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok(new TransactionStatus("OK", "Amount credited successfully!"));
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody Map<String, Double> request) {
        double amount = request.get("amount");
        try {
            accountService.withdraw(accountNumber, amount);
            return ResponseEntity.ok(new TransactionStatus("OK", "Amount debited successfully!"));
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body(new TransactionStatus("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }
}