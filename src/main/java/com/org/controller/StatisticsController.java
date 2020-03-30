package com.org.controller;

import com.org.constants.APIURIConstant;
import com.org.model.StatsInternal;
import com.org.service.StatisticsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mithun Kini on 30/Mar/2020.
 *
 * Rest API endpoints to fetch the statistics of transaction made in last 1 miniute.
 * getStatistics
 * returns following parameters
 * sum – a BigDecimal specifying the total sum of transaction value in the last 60 seconds
 * avg – a BigDecimal specifying the average amount of transaction value in the last 60 seconds
 * max – a BigDecimal specifying single highest transaction value in the last 60 seconds
 * min – a BigDecimal specifying single lowest transaction value in the last 60 seconds
 * count – a long specifying the total number of transactions that happened in the last 60 seconds
 *
 */
@RestController
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    StatisticsService statisticsService;

    @GetMapping(value = APIURIConstant.STATISTICS)
    @ApiOperation(value = "This API is used to get the statistics of transaction made in the last 60 seconds", response = StatsInternal.class)
    public StatsInternal getStatistics() throws Exception {
        logger.info("Inside StatisticsController.getStatistics");
        return statisticsService.getStatistics();
    }



    @GetMapping(value = APIURIConstant.STATISTICS_BY_INSTRUMENT_ID)
    @ApiOperation(value = "This API is used to get the statistics based on instrument of the transaction made in the last 60 seconds", response = StatsInternal.class)
    public StatsInternal getStatistics(@PathVariable String instrument_identifier) throws Exception {
        logger.info("Inside StatisticsController.getStatistics");
        return statisticsService.getStatisticsByInstrumentId(instrument_identifier);
    }

}
