package com.example.expense_teacking.sevice;


import com.example.expense_teacking.model.TansactionsDTO;
import com.example.expense_teacking.model.Transactions;
import com.example.expense_teacking.repository.TransactionRepositary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepositary transactionRepository;

    public String saveTransaction(TansactionsDTO transaction) {
        Transactions tran = new Transactions();
        tran.setCredit(transaction.getCredit());
        tran.setDebit(transaction.getDebit());
        tran.setType(transaction.getType());
        tran.setId(transaction.getId());

        transactionRepository.save(tran);
        return "Transaction saved";
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


        return Map.of("credit", creditTotal, "debit", debitTotal, "types", types);
    }
}
