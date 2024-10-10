package com.ekene.serviceurl_shortener.service;

import com.ekene.serviceurl_shortener.exception.UrlNotFoundException;
import com.ekene.serviceurl_shortener.model.Url;
import com.ekene.serviceurl_shortener.repository.UrlRepository;
import com.ekene.serviceurl_shortener.util.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    @Value("${app.base-url}")
    private String baseUrl;

    public String generateShortUrl(String longUrl) {
        Url existingUrl = urlRepository.findByLongUrl(longUrl);
        if (existingUrl != null) {
            return getFullShortUrl(existingUrl.getShortUrl());
        }
        Url url = new Url();
        url.setLongUrl(longUrl);
        url = urlRepository.save(url);
        String shortCode = Base62.generateShortUrl();
        url.setShortUrl(shortCode);

        urlRepository.save(url);
        return getFullShortUrl(url.getShortUrl());
    }

    public Url getOriginalUrl(String shortUrl) {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);

        if (urlOptional.isEmpty()){
            throw new UrlNotFoundException(String.join("No Resource found for this url ", shortUrl));
        }
        return urlOptional.get();
    }

    public String getFullShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }
}
