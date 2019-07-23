package net.psoap.newsportal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = "Comment.findAllByNews",
                query = "select c from Comment c where c.news =: newsId order by c.publishDate"),
        @NamedQuery(name = "Comment.findAllByUser",
                query = "select c from Comment c where c.user =: userId order by c.publishDate"),
        @NamedQuery(name = "Comment.deleteAllByNews",
                query = "delete from Comment c where c.news.id =: newsId"),
        @NamedQuery(name = "Comment.deleteAllByUser",
                query = "delete from Comment c where c.user =: userId"),
        @NamedQuery(name = "CommentDto.findAllByNews",
                query = "select new net.psoap.newsportal.model.dto.CommentDto(c.id, c.message, c.publishDate, c.user.email) from Comment c where c.news.id =: newsId order by c.publishDate"),
        @NamedQuery(name = "CommentDto.findAllByUser",
                query = "select new net.psoap.newsportal.model.dto.CommentDto(c.id, c.message, c.publishDate, c.message) from Comment c where c.news.id =: newsId order by c.publishDate"),
//        @NamedQuery(name = "CommentDto.findAllByUser",
//                query = "select c from CommentDto c where c.user =: userId order by c.publishDate")
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq")
    private Long id;

    @Column(length = 250, nullable = false)
    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", updatable = false)
    private News news;
}