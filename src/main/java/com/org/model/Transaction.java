package com.org.model;

import javax.validation.constraints.NotNull;

public class Transaction {
    @NotNull
    private Double amount;
    @NotNull
    private Long timestamp;
    @NotNull
    private String instrument;

    public Transaction() {
    }

    public Transaction(Double amount, Long timestamp, String instrument) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.instrument = instrument;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
