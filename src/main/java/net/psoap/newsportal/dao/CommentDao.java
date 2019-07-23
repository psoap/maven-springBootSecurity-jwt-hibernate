package net.psoap.newsportal.dao;

import net.psoap.newsportal.model.dto.CommentDto;
import net.psoap.newsportal.model.entity.Comment;

import java.util.List;

public interface CommentDao extends AbstractDao<Comment, Long> {
    List<CommentDto> findCommentsByNews(final Long newsId);
    List<CommentDto> findCommentsByUser(final Long userId);
    void deleteAllCommentsByNews(final Long newsId);
    void deleteAllCommentsByUser(final Long userId);
}
