package net.psoap.newsportal.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class NewsForm {
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    @Size(max = 2048)
    private String content;

    @NotNull
    private LocalDate changeDate;

//    public static News toNews(NewsForm newsForm){
//        return News.builder()
//                .title(newsForm.getTitle())
//                .
//    }
}