package com.example;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.ref.Cleaner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private Account account;
    private Client client;
    private Account mockedAccount;

    @Before
    public void setUp() {
        this.account = new Account();
        this.mockedAccount = mock(Account.class);
        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);
        this.client = new Client("Jane Doe", mockedAccount);
    }

    @Test
    public void shouldPrint() {
        // Reading the output written in the console.
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
