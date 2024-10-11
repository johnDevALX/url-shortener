package com.ekene.serviceurl_shortener.service;

import com.ekene.serviceurl_shortener.cache.UrlCache;
import com.ekene.serviceurl_shortener.config.AppConfig;
import com.ekene.serviceurl_shortener.exception.UrlNotFoundException;
import com.ekene.serviceurl_shortener.model.Url;
import com.ekene.serviceurl_shortener.repository.UrlRepository;
import com.ekene.serviceurl_shortener.util.UrlSlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlCache urlCache;
    private final AppConfig appConfig;

    public String generateShortUrl(String longUrl) {
        Url existingUrl = urlRepository.findByLongUrl(longUrl);
        if (existingUrl != null) {
            return getFullShortUrl(existingUrl.getShortUrl());
        }
        Url url = new Url();
        url.setLongUrl(longUrl);
        url = urlRepository.save(url);
        String shortCode = UrlSlugUtil.generateShortUrl();
        url.setShortUrl(shortCode);

        //save url in cache and database
        urlCache.addLongUrl(url);
        urlRepository.save(url);
        return getFullShortUrl(url.getShortUrl());
    }

    public Url getOriginalUrl(String shortUrl) {
        //Fetch url from cache if exist (reduce time cost for request)
        Url longUrl = urlCache.getLongUrl(shortUrl);
        if (Objects.isNull(longUrl)){
            Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);

            if (urlOptional.isEmpty()){
                throw new UrlNotFoundException(String.join("No Resource found for this url ", shortUrl));
            }
            longUrl = urlOptional.get();
        }
        return longUrl;
    }

    public String getFullShortUrl(String shortCode) {
        return appConfig.getBaseUrl() + "/v1/" + shortCode;
    }
}
