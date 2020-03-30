package com.org.controller;


import com.org.constants.APIURIConstant;
import com.org.model.StatsInternal;
import com.org.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticsController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StatisticsService statisticsService;

    @Test
    public void getStatisticsTest() throws Exception {
        StatsInternal statisticsResponseDTO = new StatsInternal(new Double(5),new Double(5),new Double(5),new Double(5),new Long(1));
        given(statisticsService.getStatistics()).willReturn(statisticsResponseDTO);

        mockMvc.perform(get(APIURIConstant.STATISTICS)
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.sum").value(5.0))
                .andExpect(jsonPath("$.avg").value(5.0))
                .andExpect(jsonPath("$.max").value(5.0))
                .andExpect(jsonPath("$.min").value(5.0))
                .andExpect(jsonPath("$.count").value(1));

    }

}
