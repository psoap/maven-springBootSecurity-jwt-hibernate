package net.psoap.newsportal.controller;

import lombok.RequiredArgsConstructor;
import net.psoap.newsportal.model.dto.CommentDto;
import net.psoap.newsportal.model.form.CommentForm;
import net.psoap.newsportal.service.CommentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "${rest.api.uri}"+"comments",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;

    @PutMapping(value ="{id}")
    public ResponseEntity editComment(@RequestBody @Valid CommentForm commentForm, @PathVariable("id") final Long id) {
        return commentService.editComment(commentForm);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity removeComment(@PathVariable("id") final Long id) {
        return commentService.removeComment(id);
    }

    @GetMapping(value = "news/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByNews(@PathVariable("id") final Long newsId) {
        return commentService.getCommentsByNews(newsId);
    }

    @PostMapping(value = "news/{id}")
    public ResponseEntity addCommentsToNews(@PathVariable("id") final Long newsId) {
        return commentService.getCommentsByNews(newsId);
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable("id") final Long userId) {
        return commentService.getCommentsByUser(userId);
    }

}