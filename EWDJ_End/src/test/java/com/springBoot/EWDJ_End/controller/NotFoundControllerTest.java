package com.springBoot.EWDJ_End.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.RequestDispatcher;

@SpringBootTest
@AutoConfigureMockMvc
class NotFoundControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
    @Test
    void testHandleErrorNotFound() throws Exception {
        mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
                .andExpect(status().isOk())
                .andExpect(view().name("error/not-found"));
    }

    @Test
    void testHandleErrorOtherErrorCodes() throws Exception {
        mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    
    @Test
    void testHandleErrorWithoutErrorCode() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
