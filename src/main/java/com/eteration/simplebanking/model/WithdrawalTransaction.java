package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;

// This class is a place holder you can change the complete implementation
public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public void execute(Account account) throws InsufficientBalanceException {
        account.withdraw(amount);
    }
}


