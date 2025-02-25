package com.example;

import com.example.exception.TransactionFailedException;
import com.example.impl.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AccountServiceTest {
    private Account account;
    private Client client;
    private Account mockedAccount;

    @BeforeEach
    public void setUp() {
        this.account = new Account();
        this.mockedAccount = mock(Account.class);
        this.client = new Client("Jane Doe", mockedAccount);
    }

    @Test
    @DisplayName("should_deposit")
    public void shouldDeposit() {
        account.deposit(1000);
        assertEquals(1000, account.getBalance());
    }

    @Test
    @DisplayName("should_withdraw")
    public void shouldWithdraw() {
        account.setBalance(2000);
        account.withdraw(1000);
        assertEquals(1000, account.getBalance());
    }

    @Test
    @DisplayName("should_throw_transaction_exception")
    public void shouldThrowException() {
        assertThrowsExactly(TransactionFailedException.class, () ->
                account.withdraw(1000)
        );
    }

    @Test
    @DisplayName("should_print_statement")
    public void shouldPrintStatement() {
        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);
        // Reading the output written in the console after print.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        account.printStatement(); // Calling the real method implementation.
        String actualOutput = outputStream.toString();
        System.setOut(System.out);

        // Giving a date for the bank account transaction (today).
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = format.format(date);
        // From the transaction we have made in setUp(), we expect to have the String bellow as output.
        String expectedStatementOutput = "Date            || Amount       || Balance\n" +
                todayDate + "      || -500         || 2500\n" +
                todayDate + "      || 2000         || 3000\n" +
                todayDate + "      || 1000         || 1000\n";

        // Real call.
        assertEquals(expectedStatementOutput, actualOutput); // Ensuring that the output is like we expect.
        client.printBankStatement(); // Calling print service from the Client object.
        // Mock call.
        verify(mockedAccount).printStatement(); // Ensuring that the method of Account class is called successfully.

    }
}
