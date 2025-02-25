package com.example.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Transaction {
    private Date date;
    private int amount;
    private int balance;
}
