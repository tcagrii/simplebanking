package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private String owner;
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new DepositTransaction(amount));
        }
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }
        balance -= amount;
        transactions.add(new WithdrawalTransaction(amount));
    }

    public void post(Transaction transaction) {
        if (transaction instanceof DepositTransaction) {
            deposit(transaction.getAmount());
        } else if (transaction instanceof WithdrawalTransaction) {
            try {
                withdraw(transaction.getAmount());
            } catch (InsufficientBalanceException e) {

            }
        }
    }
}
