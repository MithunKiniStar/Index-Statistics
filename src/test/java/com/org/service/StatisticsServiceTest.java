package com.org.service;


import com.org.model.StatsInternal;
import com.org.service.impl.StatisticsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {


    @Spy
    @InjectMocks
    StatisticsServiceImpl statisticsService;



   Map<String, StatsInternal> transactionsMap = null;
    StatsInternal statsInternal = new StatsInternal();

    @Before
    public void init(){
        transactionsMap = new ConcurrentHashMap<>();
        statsInternal.setCount(new Long(1));
        statsInternal.setMax(new Double(5));
        statsInternal.setMin(new Double(3));
        statsInternal.setSum(new Double(8));
        statsInternal.setAvg(new Double(8));
        transactionsMap.putIfAbsent("IBM",statsInternal);
    }

    @Test
    public void getStatisticsTest() throws ParseException {
        when(statisticsService.getStatistics()).thenReturn(statsInternal);
        StatsInternal statistics = statisticsService.getStatistics();
        assertEquals(new Long(1),statistics.getCount());
        assertEquals(new Double(8),statistics.getSum());
        assertEquals(new Double(8),statistics.getAvg());
        assertEquals(new Double(5),statistics.getMax());
        assertEquals(new Double(3),statistics.getMin());
    }
}
