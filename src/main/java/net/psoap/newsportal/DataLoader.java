package net.psoap.newsportal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.psoap.newsportal.dao.CommentDao;
import net.psoap.newsportal.dao.NewsDao;
import net.psoap.newsportal.dao.UserDao;
import net.psoap.newsportal.model.entity.*;
import net.psoap.newsportal.model.enums.UserRole;
import net.psoap.newsportal.model.enums.UserState;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Slf4j
public class DataLoader implements ApplicationRunner {
    private final UserDao userDao;
    private final NewsDao newsDao;
    private final CommentDao commentDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User admin = User.builder()
                .email("admin@mail.com")
                .password(passwordEncoder.encode("admin123"))
                .userRole(UserRole.ADMIN)
                .userState(UserState.ACTIVE)
                .build();
        userDao.insert(admin);

        News news1 = new News(null, "title1", "content1", LocalDate.now(), admin, null);
        News news2 = new News(null, "title2", "content2", LocalDate.now(), admin, null);
        News news3 = new News(null, "title3", "content3", LocalDate.now(), admin, null);
        News news4 = new News(null, "title4", "content4", LocalDate.now(), admin, null);

        newsDao.insert(news1);
        newsDao.insert(news2);
        newsDao.insert(news3);
        newsDao.insert(news4);

        Comment comment1 = new Comment(null, "comment1", LocalDate.now(), admin, news1);
        Comment comment2 = new Comment(null, "comment2", LocalDate.now(), admin, news1);
        Comment comment3 = new Comment(null, "comment3", LocalDate.now(), admin, news1);

        commentDao.insert(comment1);
        commentDao.insert(comment2);
        commentDao.insert(comment3);

        log.info("Init data has loaded");
    }
}
