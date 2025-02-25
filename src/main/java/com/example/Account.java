package com.example;

import com.example.exception.TransactionFailedException;
import com.example.impl.Transaction;
import com.example.service.AccountService;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Account implements AccountService {
    @Getter@Setter
    private int balance;
    @Getter
    private final List<Transaction> transactions;
    private long lastTimestamp = 0;

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    @Override
    public void deposit(int amount) {
        // The idea is to ensure that no transaction would be made in the exact same time.
        long now = System.currentTimeMillis();

        if (now <= lastTimestamp) {
            now = lastTimestamp + 1; // Here ensuring unique timestamps.
        }
        lastTimestamp = now;

        Date date = new Date(now);
        this.balance += amount;
        transactions.add(new Transaction(date, amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        if (this.balance < amount) {
            throw new TransactionFailedException("Your account has no sufficient funds");
        }
        long now = System.currentTimeMillis();

        if (now <= lastTimestamp) {
            now = lastTimestamp + 1;
        }
        lastTimestamp = now;

        Date date = new Date(now);
        this.balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));
    }

    @Override
    public void printStatement() {
        StringBuilder statement = new StringBuilder("Date            || Amount       || Balance\n");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        transactions.stream().sorted(Comparator.comparing(Transaction::getDate).reversed()).collect(Collectors.toList())
                .forEach(transaction ->
                        statement.append(dateFormat.format(transaction.getDate()))
                                .append("      || ")
                                .append(transaction.getAmount())
                                .append("         || ")
                                .append(transaction.getBalance())
                                .append("\n")
                );
        System.out.print(statement);
    }
}
