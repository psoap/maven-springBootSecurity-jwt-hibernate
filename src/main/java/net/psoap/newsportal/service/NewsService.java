package net.psoap.newsportal.service;

import net.psoap.newsportal.model.dto.NewsFullDto;
import net.psoap.newsportal.model.form.NewsForm;
import org.springframework.http.ResponseEntity;

public interface NewsService {
    ResponseEntity addNews(final NewsForm newsForm);
    ResponseEntity editNews(final Long id, final NewsForm newsForm);
    ResponseEntity removeNews(final Long id);
    ResponseEntity<NewsFullDto> getNewsById(final Long id);
}
