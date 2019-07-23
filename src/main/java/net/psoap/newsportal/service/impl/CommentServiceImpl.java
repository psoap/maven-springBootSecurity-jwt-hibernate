package net.psoap.newsportal.service.impl;

import lombok.RequiredArgsConstructor;
import net.psoap.newsportal.dao.CommentDao;
import net.psoap.newsportal.model.dto.CommentDto;
import net.psoap.newsportal.model.entity.Comment;
import net.psoap.newsportal.model.entity.News;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.form.CommentForm;
import net.psoap.newsportal.security.UserDetailsImpl;
import net.psoap.newsportal.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    @Override
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity addComment(CommentForm commentForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Comment comment = Comment.builder()
                .message(commentForm.getMessage())
                .publishDate(LocalDate.now())
                .news(News.builder()
                        .id(commentForm.getNewsId())
                        .build())
                .user(User.builder().id(userDetails.getId()).build())
                .build();

        commentDao.insert(comment);

        return ResponseEntity.accepted().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity editComment(CommentForm commentForm) {
        Optional<Comment> commentDb = commentDao.findOneById(commentForm.getId());
        return commentDb
                .map(comment -> {
                    comment.setMessage(commentForm.getMessage());
                    commentDao.update(comment);
                    return ResponseEntity.accepted().build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity removeComment(Long id) {
        Optional<Comment> commentDb = commentDao.findOneById(id);
        return commentDb
                .map(comment -> {
                    commentDao.delete(comment);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<CommentDto>> getCommentsByNews(Long newsId) {
        return ResponseEntity.ok(commentDao.findCommentsByNews(newsId));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<CommentDto>> getCommentsByUser(Long userId) {
        return ResponseEntity.ok(commentDao.findCommentsByUser(userId));
    }

}