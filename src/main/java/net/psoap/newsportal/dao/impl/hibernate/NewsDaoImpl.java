package net.psoap.newsportal.dao.impl.hibernate;

import net.psoap.newsportal.dao.NewsDao;
import net.psoap.newsportal.model.dto.NewsBriefDto;
import net.psoap.newsportal.model.entity.News;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDao<News> implements NewsDao {

    @Override
    public News findNewsByUri(LocalDate date, String uri) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NewsBriefDto> findNewsListByPage(int offset, int limit) {
        return getEntityManager().createNamedQuery("NewsBriefDto.findAll")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}