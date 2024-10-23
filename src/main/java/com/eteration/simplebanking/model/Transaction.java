package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;

import java.util.Date;

public abstract class Transaction {
    protected double amount;
    protected Date date;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = new Date();
    }

    public abstract void execute(Account account) throws InsufficientBalanceException;

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
