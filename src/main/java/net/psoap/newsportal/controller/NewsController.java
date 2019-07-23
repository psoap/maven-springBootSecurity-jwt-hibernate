package net.psoap.newsportal.controller;

import lombok.RequiredArgsConstructor;
import net.psoap.newsportal.model.dto.NewsFullDto;
import net.psoap.newsportal.model.form.NewsForm;
import net.psoap.newsportal.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("${rest.api.uri}"+"news")
public class NewsController {
    private final NewsService newsService;

    //TODO
    @GetMapping
    public String getAllNews(Authentication authentication){
        return authentication.getPrincipal().toString();
    }

    @GetMapping(value = "{id}")
    @CrossOrigin
    public ResponseEntity<NewsFullDto> getNewsById(@PathVariable("id") final Long id){
        return newsService.getNewsById(id);
    }

    @PostMapping
    public ResponseEntity<NewsFullDto> createNews(@RequestBody @Valid final NewsForm newsForm){
        return newsService.addNews(newsForm);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<NewsFullDto> updateNews(@PathVariable("id") final Long id,
                                                 @RequestBody @Valid final NewsForm newsForm){
        return newsService.editNews(id, newsForm);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteNews(@PathVariable("id") final Long id){
        return newsService.removeNews(id);
    }
}
