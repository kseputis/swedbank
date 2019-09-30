package com.swedbank.decathlon.result.calculator.api;

import com.swedbank.decathlon.result.calculator.CustomerErrorHandler;
import com.swedbank.decathlon.result.calculator.services.DecathlonResultCalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DecathlonResultCalculatorController.class)
public class DecathlonResultCalculatorControllerTest {
    @Autowired
    private MockMvc mvc;
    @InjectMocks
    private DecathlonResultCalculatorController controller;
    @MockBean
    private DecathlonResultCalculatorService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomerErrorHandler())
                .build();
    }

    @Test
    public void calculateResult_file_status200() throws Exception {
        MockMultipartFile mockMultipartFile = createMockMultipartFile("test_data.csv");

        mvc.perform(multipart("/calculator/calculate/")
                .file(mockMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void calculateResult_badFile_exceptionThrown() throws Exception {
        MockMultipartFile mockMultipartFile = createMockMultipartFile("bad_test_data.csv");

        mvc.perform(multipart("/calculator/calculate/")
                .file(mockMultipartFile))
                .andExpect(status().is4xxClientError());
    }

    private MockMultipartFile createMockMultipartFile(String fileName) throws IOException {
        return new MockMultipartFile("file", getClass().getClassLoader().getResourceAsStream(fileName));
    }

}
