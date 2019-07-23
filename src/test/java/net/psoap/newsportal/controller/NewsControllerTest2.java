package net.psoap.newsportal.controller;

import net.psoap.newsportal.config.MvcConfig;
import net.psoap.newsportal.config.WebSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
@ContextConfiguration(classes = {MvcConfig.class, WebSecurityConfig.class})
class NewsControllerTest2 {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Get news by id")
    void shouldReturnNewsById() throws Exception {
        long newsId = 1L;

        MockHttpServletResponse  response = mockMvc.perform(
                get("api/v1/news/"+newsId)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        System.out.println(response.getContentAsString());
    }

}