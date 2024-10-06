package com.example.expense_teacking.controller;

import com.example.expense_teacking.model.TansactionsDTO;
import com.example.expense_teacking.model.Transactions;
import com.example.expense_teacking.sevice.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/add")
    public String addTransaction(@RequestBody TansactionsDTO transaction){
        return transactionService.saveTransaction(transaction);
    }
    @GetMapping("/get/{date}")
    public Map<String, Object> getTransactionByDate(@PathVariable String date){
        return transactionService.getTransactionByDate(date);
    }
}
