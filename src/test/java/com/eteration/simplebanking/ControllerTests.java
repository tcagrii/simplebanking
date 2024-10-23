package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.TransactionStatus;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests {

    @Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    @Test
    public void givenId_Credit_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");
        account.deposit(0);

        doReturn(account).when(service).getAccount("17892");

        Map<String, Double> request = new HashMap<>();
        request.put("amount", 1000.0);

        ResponseEntity<TransactionStatus> result = controller.credit("17892", request);
        verify(service, times(1)).deposit("17892", 1000.0);
        assertEquals("OK", result.getBody().getStatus());

    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson() throws InsufficientBalanceException {
        Account account = new Account("Kerem Karaca", "17892");
        account.deposit(1000.0);

        doReturn(account).when(service).getAccount("17892");

        Map<String, Double> creditRequest = new HashMap<>();
        creditRequest.put("amount", 1000.0);
        controller.credit("17892", creditRequest);

        verify(service, times(1)).deposit("17892", 1000.0);

        Map<String, Double> debitRequest = new HashMap<>();
        debitRequest.put("amount", 50.0);
        ResponseEntity<TransactionStatus> result2 = controller.debit("17892", debitRequest);
        verify(service, times(1)).withdraw("17892", 50.0);

        assertEquals("OK", result2.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");
        account.deposit(1000.0);

        doReturn(account).when(service).getAccount("17892");

        Map<String, Double> creditRequest = new HashMap<>();
        creditRequest.put("amount", 1000.0);

        ResponseEntity<TransactionStatus> creditResult = controller.credit("17892", creditRequest);
        assertEquals("OK", creditResult.getBody().getStatus());
        assertEquals(1000.0, account.getBalance(), 0.001);

        Map<String, Double> debitRequest = new HashMap<>();
        debitRequest.put("amount", 5000.0);


    }

    @Test
    public void givenId_GetAccount_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).getAccount("17892");
        ResponseEntity<Account> result = controller.getAccount("17892");
        verify(service, times(1)).getAccount("17892");
        assertEquals(account, result.getBody());
    }
}
