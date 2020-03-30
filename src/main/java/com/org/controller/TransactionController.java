package com.org.controller;

import com.org.constants.APIURIConstant;
import com.org.exception.InvalidDateException;
import com.org.model.Transaction;
import com.org.service.StatisticsService;
//import com.org.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Mithun Kini on 30/Mar/2020.
 *
 * Rest API endpoints to record transaction and clear all transaction.
 * recordTransaction - POST /ticks – called every time a transaction is made.
 *
 * Returns: Empty body with one of the following:
 * 201 – in case of success
 * 204 – if the transaction is older than 60 seconds
 * 400 – if the JSON is invalid
 * 422 – if any of the fields are not parsable or the transaction date is in the future
 *
 *  *
 *
 */
@RestController
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    StatisticsService statisticsService;


    @PostMapping(value = APIURIConstant.TRANSACTIONS)
    @ApiOperation(value = "This API is used to make the transaction")
    public ResponseEntity<Void> recordTransaction(@Valid @RequestBody Transaction transactionRequestDTO) throws InvalidDateException {
        logger.info("Inside TransactionController.recordTransaction");
        int status = statisticsService.addTransaction(transactionRequestDTO);
        if(status==HttpStatus.CREATED.value()) {
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }else  {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }


}
