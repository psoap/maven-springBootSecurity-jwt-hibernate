package net.psoap.newsportal.controller;

import net.psoap.newsportal.TestUtils;
import net.psoap.newsportal.model.dto.NewsFullDto;
import net.psoap.newsportal.model.form.NewsForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
class NewsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private final String CONTROLLER_URI = "news";

    @Test
    @DisplayName("Get news by id")
    void shouldReturnNewsById(){
        long newsId = 1L;
        ResponseEntity<NewsFullDto> newsResponse = restTemplate.getForEntity(TestUtils.URL+CONTROLLER_URI+"/"+newsId, NewsFullDto.class);
        assertEquals(newsResponse.getStatusCode(), HttpStatus.OK);
        assertNotNull(newsResponse.getBody());
    }

    @Test
    @DisplayName("Add news")
    void shouldAddNews(){
        NewsForm news = new NewsForm();
        news.setTitle("junit title");
        news.setContent("Junit content");
        news.setChangeDate(LocalDate.now());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(TestUtils.BEARER_TOKEN);

        HttpEntity<NewsForm> entity = new HttpEntity<>(news, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(TestUtils.URL+CONTROLLER_URI, entity, String.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

}