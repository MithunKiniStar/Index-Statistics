package com.org.service.impl;

import com.org.model.StatsInternal;
import com.org.model.Transaction;
import com.org.service.StatisticsService;
import com.org.ttl.PrintAPIHits;
import com.org.ttl.TTLConcurrentHashMap;
import com.org.ttl.TTLConcurrentHashMapListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

/**
 * @author Mithun Kini on 30/Mar/2020.
 *
 * StatisticsServiceImpl -
 * addTransaction() --> adds transaction records evetrytime ticks is called
 * getStatistics() -->  returns the statistic based of the transactions of the last 60 seconds.
 * getStatisticsByInstrumentId --> returns the statistic based of the transactions of the last 60 seconds of a particular instrument.
 *
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);


    private static final int SUMMARY_WINDOW = 60000;
    private TTLConcurrentHashMap<String, StatsInternal> statsInWindow = new TTLConcurrentHashMap<>(2 * 60 * 1000);	// Keys in the map will expire in 2 minutes


    public StatisticsServiceImpl() {
        statsInWindow.registerRemovalListener(new PrintAPIHits());
    }

    @Override
    public int addTransaction(Transaction transaction) {
        logger.info("Inside addTransaction");
        logger.debug("Inside addTransaction" + transaction);
        if(!validateTransaction(transaction)) {
            logger.error("Invalid transaction timestamp.");
            return 204;
        }

        statsInWindow.compute(transaction.getInstrument(), (secondKey, stats)-> {

            if(null == stats || System.currentTimeMillis() - stats.getTimestamp() >= SUMMARY_WINDOW) {
                    // discard old statsInWindow
                stats = new StatsInternal();
                stats.setTimestamp(transaction.getTimestamp());
                stats.setSum(transaction.getAmount());
                stats.setMin(transaction.getAmount());
                stats.setMax(transaction.getAmount());
                stats.setCount(1L);
                stats.setAvg(transaction.getAmount());
                return stats;
            }

            stats.setSum(stats.getSum() + transaction.getAmount());
            if(transaction.getAmount() > stats.getMax())
                stats.setMax(transaction.getAmount());
            if(transaction.getAmount() < stats.getMin())
                stats.setMin(transaction.getAmount());
            stats.setCount(stats.getCount() + 1);
            stats.setAvg(stats.getSum()/stats.getCount());
            return stats;
        });
        statsInWindow.recordEntry(transaction.getInstrument());
        return 201;
    }

    @Override
    public StatsInternal getStatisticsByInstrumentId(String instrumentIdentifier) {
        logger.info("Inside getStatisticsByInstrumentId");
        logger.debug("Inside getStatisticsByInstrumentId" + instrumentIdentifier);
        return statsInWindow.get(instrumentIdentifier);
    }

    private boolean validateTransaction(Transaction transaction) {
        if(transaction.getTimestamp() < System.currentTimeMillis() && System.currentTimeMillis() - transaction.getTimestamp() < SUMMARY_WINDOW) {
            return true;
        }
        return false;
    }

    @Override
    public StatsInternal getStatistics() throws ParseException {
        logger.info("Inside getStatistics");

        Optional<StatsInternal> statistics = statsInWindow.values().stream()
                .filter(stats -> {
                    return System.currentTimeMillis() - stats.getTimestamp() < SUMMARY_WINDOW;
                })
                .map(StatsInternal::create)
                .reduce((statistics1, statistics2) -> {
                    statistics1.setSum(statistics1.getSum() + statistics2.getSum());
                    if(statistics2.getMax() > statistics1.getMax())
                        statistics1.setMax(statistics2.getMax());
                    if(statistics2.getMin() < statistics1.getMin())
                        statistics1.setMin(statistics2.getMin());
                    statistics1.setCount(statistics1.getCount() + statistics2.getCount());
                    return statistics1;
                });

        if(statistics.isPresent()) {
            if(statistics.get().getCount() > 0L) {
                statistics.get().setAvg(statistics.get().getSum() / statistics.get().getCount());
            }
        }
        return statistics.orElse(new StatsInternal());
    }


}

