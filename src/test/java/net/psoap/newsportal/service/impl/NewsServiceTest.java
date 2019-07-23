package net.psoap.newsportal.service.impl;

import net.psoap.newsportal.dao.CommentDao;
import net.psoap.newsportal.dao.NewsDao;
import net.psoap.newsportal.model.entity.News;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.form.NewsForm;
import net.psoap.newsportal.security.UserDetailsImpl;
import net.psoap.newsportal.util.HttpHeadersUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpHeadersUtil.class})
public class NewsServiceTest {
    @Mock(answer = RETURNS_DEEP_STUBS)
    SecurityContext securityContext;

    @Mock
    NewsDao newsDao;

    @Mock
    CommentDao commentDao;

    @Spy
    @InjectMocks
    NewsServiceImpl newsService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNews() {
        NewsForm newsForm = new NewsForm();
        newsForm.setTitle("title");
        newsForm.setContent("content");
        newsForm.setChangeDate(LocalDate.now());

        User user = User.builder().id(1L).build();

        News newsForChecking = News.builder()
                .title(newsForm.getTitle())
                .content(newsForm.getContent())
                .changeDate(newsForm.getChangeDate())
                .user(user)
                .build();

        SecurityContextHolder.setContext(securityContext);


        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(new UserDetailsImpl(user));

        PowerMockito.mockStatic(HttpHeadersUtil.class);
        when(HttpHeadersUtil.buildLocationFromId(ArgumentMatchers.anyLong())).thenReturn(ArgumentMatchers.any(URI.class));

        ResponseEntity responseEntity = newsService.addNews(newsForm);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(responseEntity.getHeaders().getLocation());
        verify(newsDao, times(1)).insert(ArgumentMatchers.any(News.class));

    }

    @Test
    public void editNews() {
    }

    @Test
    public void removeNews() {
    }

    @Test
    public void getNewsById() {
    }
}