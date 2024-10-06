package com.example.expense_teacking.repository;

import com.example.expense_teacking.model.TansactionsDTO;
import com.example.expense_teacking.model.Transactions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepositary extends JpaRepository<Transactions, Long>{

    List<Transactions> findByDate(String date);
}
