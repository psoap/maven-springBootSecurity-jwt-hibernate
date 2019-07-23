package net.psoap.newsportal.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHeadersUtil {

    public static URI buildLocationFromId(Long id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}