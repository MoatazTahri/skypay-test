package com.example.impl;

import com.example.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private String fullName;
    private Account account;

    public void printBankStatement() {
        account.printStatement();
    }
}
