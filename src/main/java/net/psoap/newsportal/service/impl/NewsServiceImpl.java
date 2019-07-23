package net.psoap.newsportal.service.impl;

import lombok.RequiredArgsConstructor;
import net.psoap.newsportal.dao.CommentDao;
import net.psoap.newsportal.dao.NewsDao;
import net.psoap.newsportal.model.dto.NewsFullDto;
import net.psoap.newsportal.model.entity.News;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.form.NewsForm;
import net.psoap.newsportal.security.UserDetailsImpl;
import net.psoap.newsportal.service.NewsService;
import net.psoap.newsportal.util.HttpHeadersUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service("newsService")
public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao;
    private final CommentDao commentDao;

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity addNews(NewsForm newsForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        News news = News.builder()
                .title(newsForm.getTitle())
                .content(newsForm.getContent())
                .changeDate(newsForm.getChangeDate())
                .user(User.builder().id(userDetails.getId()).build())
                .build();

        newsDao.insert(news);

        return ResponseEntity.created(HttpHeadersUtil.buildLocationFromId(news.getId())).build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity editNews(Long id, NewsForm newsForm) {
        Optional<News> newsDb = newsDao.findOneById(id);
        return newsDb
                .map(news -> {
                    news.setTitle(newsForm.getTitle());
                    news.setContent(newsForm.getContent());
                    news.setChangeDate(newsForm.getChangeDate());
                    newsDao.update(news);
                    return ResponseEntity.created(HttpHeadersUtil.buildLocationFromId(news.getId())).build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity removeNews(Long id) {
        Optional<News> newsDb = newsDao.findOneById(id);
        return newsDb
                .map(news -> {
                    commentDao.deleteAllCommentsByNews(id);
                    newsDao.delete(news);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ResponseEntity<NewsFullDto> getNewsById(Long id) {
        Optional<News> newsDb = newsDao.findOneById(id);
        return newsDb
                .map(news -> ResponseEntity.ok(NewsFullDto.from(news)))
                .orElse(ResponseEntity.notFound().build());
    }
}