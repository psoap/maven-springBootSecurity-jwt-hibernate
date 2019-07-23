package net.psoap.newsportal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String message;
    private LocalDate publishDate;
    private String email;
}