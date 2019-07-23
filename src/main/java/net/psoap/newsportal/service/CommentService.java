package net.psoap.newsportal.service;

import net.psoap.newsportal.model.dto.CommentDto;
import net.psoap.newsportal.model.form.CommentForm;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity addComment(final CommentForm commentForm);
    ResponseEntity editComment(final CommentForm commentForm);
    ResponseEntity removeComment(final Long id);
    ResponseEntity<List<CommentDto>> getCommentsByNews(final Long newsId);
    ResponseEntity<List<CommentDto>> getCommentsByUser(final Long userId);
}
