package net.psoap.newsportal.dao.impl.hibernate;

import net.psoap.newsportal.dao.CommentDao;
import net.psoap.newsportal.model.dto.CommentDto;
import net.psoap.newsportal.model.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentDao")
public class CommentDaoIml extends BaseDao<Comment> implements CommentDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<CommentDto> findCommentsByNews(Long newsId) {
        return getEntityManager().createNamedQuery("CommentDto.findAllByNews")
                .setParameter("newsId", newsId).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommentDto> findCommentsByUser(Long userId) {
        return getEntityManager().createNamedQuery("Comment.findAllByNews")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public void deleteAllCommentsByNews(Long newsId) {
        getEntityManager().createNamedQuery("Comment.deleteAllByNews")
                .setParameter("newsId", newsId).executeUpdate();
    }

    @Override
    public void deleteAllCommentsByUser(Long userId) {
        getEntityManager().createNamedQuery("Comment.deleteAllByUser")
                .setParameter("userId", userId).executeUpdate();
    }
}
