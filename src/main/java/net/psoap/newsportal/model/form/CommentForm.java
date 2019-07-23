package net.psoap.newsportal.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CommentForm {
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String message;

    @NotNull
    private LocalDate publishDate;

    @NotNull
    private Long newsId;
}
