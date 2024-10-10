package com.ekene.serviceurl_shortener.service;

import com.ekene.serviceurl_shortener.exception.UrlNotFoundException;
import com.ekene.serviceurl_shortener.model.Url;
import com.ekene.serviceurl_shortener.repository.UrlRepository;
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
        String shortCode = generateShortUrl();
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

    private String generateShortUrl() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            shortUrl.append(characters.charAt(index));
        }
        return shortUrl.toString();
    }
}
