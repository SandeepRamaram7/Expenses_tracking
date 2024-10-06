package com.example.expense_teacking.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
public class TansactionsDTO {

    private Long id;


    private String date;

    private double credit;

    private double debit;

    private String type;
}
