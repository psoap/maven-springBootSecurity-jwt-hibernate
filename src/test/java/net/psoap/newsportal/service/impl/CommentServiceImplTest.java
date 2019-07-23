package net.psoap.newsportal.service.impl;

import net.psoap.newsportal.dao.CommentDao;
import net.psoap.newsportal.model.dto.CommentDto;
import net.psoap.newsportal.model.entity.Comment;
import net.psoap.newsportal.model.entity.News;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.form.CommentForm;
import net.psoap.newsportal.security.UserDetailsImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    CommentServiceImpl commentService;

    @Mock
    CommentDao commentDao;

    @Before
    public void setup() {
        commentService = new CommentServiceImpl(this.commentDao);
    }

    @Test
    public void add() {
        CommentForm commentForm = new CommentForm();
        commentForm.setMessage("msg");
        commentForm.setNewsId(123L);

        User user = User.builder().id(456L).build();

        SecurityContext securityContext = mock(SecurityContext.class, Answers.RETURNS_DEEP_STUBS);
        SecurityContextHolder.setContext(securityContext);
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(new UserDetailsImpl(user));

        ResponseEntity responseEntity = commentService.addComment(commentForm);

        verify(commentDao, times(1))
                .insert(commentArgumentCaptor.capture());

        Comment comment = commentArgumentCaptor.getValue();

        assertNotNull(comment.getPublishDate());
        assertEquals(comment.getMessage(), commentForm.getMessage());
        assertEquals(comment.getUser().getId(), user.getId());
        assertEquals(comment.getNews().getId(), commentForm.getNewsId());
        assertEquals(ResponseEntity.accepted().build(), responseEntity);
    }

    @Test
    public void edit() {
        CommentForm commentForm = new CommentForm();
        commentForm.setId(147L);
        commentForm.setMessage("new-msg");

        Comment comment = Comment.builder()
                .id(commentForm.getId())
                .message("old-msg")
                .publishDate(LocalDate.now())
                .news(News.builder().id(963L).build())
                .user(User.builder().id(123L).build())
                .build();

        when(commentDao.findOneById(commentForm.getId())).thenReturn(Optional.of(comment));

        ResponseEntity responseEntity = commentService.editComment(commentForm);

        comment.setMessage(commentForm.getMessage());
        verify(commentDao, times(1)).update(comment);
        assertEquals(ResponseEntity.accepted().build(), responseEntity);
    }

    @Test
    public void edit2() {
        CommentForm commentForm = new CommentForm();
        commentForm.setId(147L);

        when(commentDao.findOneById(commentForm.getId())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = commentService.editComment(commentForm);

        assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }

    @Test
    public void delete() {
        Comment comment = Comment.builder()
                .id(147L)
                .message("msg")
                .publishDate(LocalDate.now())
                .news(News.builder().id(963L).build())
                .user(User.builder().id(123L).build())
                .build();

        when(commentDao.findOneById(comment.getId())).thenReturn(Optional.of(comment));
        ResponseEntity responseEntity = commentService.removeComment(comment.getId());

        verify(commentDao, times(1)).delete(comment);
        assertEquals(ResponseEntity.noContent().build(), responseEntity);
    }

    @Test
    public void delete2() {
        final long id = 147L;

        when(commentDao.findOneById(id)).thenReturn(Optional.empty());
        ResponseEntity responseEntity = commentService.removeComment(id);

        assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }

    @Test
    public void get() {
        final long id = 147L;
        List<CommentDto> resultList = Collections.singletonList(CommentDto.builder().id(id).build());

        when(commentDao.findCommentsByNews(id)).thenReturn(resultList);
        ResponseEntity responseEntity = commentService.getCommentsByNews(id);

        assertEquals(ResponseEntity.ok(resultList), responseEntity);
    }

    @Test
    public void get2() {
        final long id = 147L;
        List<CommentDto> resultList = Collections.singletonList(CommentDto.builder().id(id).build());

        when(commentDao.findCommentsByUser(id)).thenReturn(resultList);
        ResponseEntity responseEntity = commentService.getCommentsByUser(id);

        assertEquals(ResponseEntity.ok(resultList), responseEntity);
    }
}