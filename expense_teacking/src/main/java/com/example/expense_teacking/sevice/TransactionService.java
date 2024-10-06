package com.example.expense_teacking.sevice;


import com.example.expense_teacking.model.TansactionsDTO;
import com.example.expense_teacking.model.Transactions;
import com.example.expense_teacking.repository.TransactionRepositary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepositary transactionRepository;

    public Transactions saveTransaction(TansactionsDTO transaction) {
        Transactions latestTransaction = transactionRepository.findFirstByOrderByIdDesc();
        double existingBalance = latestTransaction != null ? latestTransaction.getBalance() : 0;
        Transactions tran = new Transactions();
        tran.setCredit(transaction.getCredit());
        tran.setDate(transaction.getDate());
        tran.setDebit(transaction.getDebit());
        tran.setType(transaction.getType());
        tran.setId(transaction.getId());
        tran.setBalance(existingBalance + transaction.getCredit() - transaction.getDebit());

        return transactionRepository.save(tran);

    }

    public Map<String, Object> getTransactionByDate(String date) {
//        LocalDate transactionDate = LocalDate.parse(date);


        List<Transactions> transactions = transactionRepository.findByDate(date);

        double creditTotal = transactions.stream()
                .mapToDouble(transaction -> transaction.getCredit())
                .sum();
        double debitTotal = transactions.stream()
                .mapToDouble(transaction -> transaction.getDebit())
                .sum();

        List<String> types = transactions.stream()
                .map(transaction -> transaction.getType())
                .distinct()
                .toList();

        double existingBalance = getBalance();


        return Map.of("credit", creditTotal, "debit", debitTotal, "types", types,"Balance",existingBalance);
    }

    public Map<String, Double> getMonthlyTotals(@RequestParam String month) {


        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
        LocalDate monthStart;
        LocalDate monthEnd;

        try {
            // Parse the month and year and set the day to 1
            monthStart = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("MM-yyyy-dd"));
            monthEnd = monthStart.plusMonths(1).minusDays(1);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return Map.of("credit", 0.0, "debit", 0.0);
        }



        // Format dates in "dd-MM-yyyy"
        String monthStartstr = monthStart.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String monthEndstr = monthEnd.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Log the formatted dates
        System.out.println("Fetching transactions from " + monthStartstr + " to " + monthEndstr);

        List<Transactions> transactions = transactionRepository.findByDateBetween(monthStartstr, monthEndstr);
        System.out.println("Transactions fetched: " + transactions.size());

        for (Transactions transaction : transactions) {
            System.out.println(transaction);
        }

        double creditTotal = transactions.stream()
                .mapToDouble(transaction -> transaction.getCredit())
                .sum();
        double debitTotal = transactions.stream()
                .mapToDouble(transaction -> transaction.getDebit())
                .sum();

        Double existingBalance = getBalance();

        return Map.of("credit", creditTotal, "debit", debitTotal,"Balance",existingBalance);
    }
    public Double getBalance(){
        Transactions latestTransaction = transactionRepository.findFirstByOrderByIdDesc();
        double existingBalance = latestTransaction != null ? latestTransaction.getBalance() : 0;
        return existingBalance;
    }
}

