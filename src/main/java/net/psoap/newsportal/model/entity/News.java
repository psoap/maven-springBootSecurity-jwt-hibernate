package net.psoap.newsportal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "NewsBriefDto.findAll",
                query = "select new net.psoap.newsportal.model.dto.NewsBriefDto(n.id, n.title, n.changeDate) from News n order by n.changeDate"),
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "NEWS")
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_id_seq")
    @SequenceGenerator(name = "news_id_seq", sequenceName = "news_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(length = 2048, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate changeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("publishDate ASC")
    private List<Comment> comments;

//    private List<String> tags;
}
