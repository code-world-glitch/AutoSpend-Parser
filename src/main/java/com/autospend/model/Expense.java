package com.autospend.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Expense {

    private Long id;
    private LocalDate purchaseDate;
    private String merchant;
    private BigDecimal amount;
    private String category;
    private LocalDateTime processedAt;

    public Expense(){}

    public Expense(
            LocalDate purchaseDate,
            String merchant,
            BigDecimal amount,
            String category) {

        this.purchaseDate=purchaseDate;
        this.merchant=merchant;
        this.amount=amount;
        this.category=category;
        this.processedAt=LocalDateTime.now();
    }

    public Long getId() {return id;}
    public void setId(Long id){this.id=id;}

    public LocalDate getPurchaseDate(){return purchaseDate;}
    public void setPurchaseDate(LocalDate d){purchaseDate=d;}

    public String getMerchant(){return merchant;}
    public void setMerchant(String merchant){this.merchant=merchant;}

    public BigDecimal getAmount(){return amount;}
    public void setAmount(BigDecimal amount){this.amount=amount;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category=category;}

    public LocalDateTime getProcessedAt(){return processedAt;}
    public void setProcessedAt(LocalDateTime t){processedAt=t;}
}