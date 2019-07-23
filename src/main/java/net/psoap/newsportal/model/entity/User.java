package net.psoap.newsportal.model.entity;

import lombok.*;
import net.psoap.newsportal.model.enums.UserRole;
import net.psoap.newsportal.model.enums.UserState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email =: email")
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true, length = 48)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private UserState userState;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    private List<News> news;
}