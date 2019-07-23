package net.psoap.newsportal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.psoap.newsportal.model.entity.News;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsFullDto {
    private String title;
    private String content;
    private LocalDate changeDate;

    public static NewsFullDto from(final News news){
        return NewsFullDto.builder()
                .title(news.getTitle())
                .content(news.getContent())
                .changeDate(news.getChangeDate())
                .build();
    }
}
