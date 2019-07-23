package net.psoap.newsportal.controller.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<String> keys;

    public ApiError(List<ObjectError> errors){
        keys = new ArrayList<>(errors.size());
        errors.forEach(objectError -> keys.add(objectError.getDefaultMessage()));
    }

    public ApiError(String key){
        this.keys = Collections.singletonList(key);
    }

}