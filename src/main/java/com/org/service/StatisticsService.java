package com.org.service;

import com.org.model.StatsInternal;
import com.org.model.Transaction;

import java.text.ParseException;

public interface StatisticsService {
    public StatsInternal getStatistics() throws ParseException;

    public int addTransaction(Transaction transaction) ;

    public StatsInternal getStatisticsByInstrumentId(String instrumentIdentifier);
}
