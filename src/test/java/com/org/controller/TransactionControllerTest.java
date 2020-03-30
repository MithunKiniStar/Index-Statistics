package com.org.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.constants.APIURIConstant;
import com.org.model.Transaction;
import com.org.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StatisticsService statisticsService;

    @Test
    public void recordTransactionTest() throws Exception {
        Transaction transaction = new Transaction(new Double(200),System.currentTimeMillis(),"IBM");
        given(statisticsService.addTransaction(transaction)).willReturn(201);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(APIURIConstant.TRANSACTIONS)
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().is2xxSuccessful());
    }

}
