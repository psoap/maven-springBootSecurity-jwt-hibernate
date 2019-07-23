package net.psoap.newsportal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.psoap.newsportal.model.entity.News;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class NewsBriefDto {
    private Long id;
    private String title;
    private LocalDate changeDate;

    public static NewsBriefDto from(final News news){
        return NewsBriefDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .changeDate(news.getChangeDate())
                .build();
    }
}
