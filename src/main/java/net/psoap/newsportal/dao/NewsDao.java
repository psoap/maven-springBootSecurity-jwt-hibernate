package net.psoap.newsportal.dao;


import net.psoap.newsportal.model.dto.NewsBriefDto;
import net.psoap.newsportal.model.entity.News;

import java.time.LocalDate;
import java.util.List;

public interface NewsDao extends AbstractDao<News, Long> {
    News findNewsByUri(final LocalDate date, final String uri);
    List<NewsBriefDto> findNewsListByPage(final int offset, final int limit);
}
