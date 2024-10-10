package com.ekene.serviceurl_shortener.cache;

import com.ekene.serviceurl_shortener.model.Url;
import lombok.RequiredArgsConstructor;
import org.redisson.api.EvictionMode;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class UrlCache {

    /**
     * Saves the recent urls to the cache
     * @Param
     *
     */

    private final RedissonClient redissonClient;

    public void addLongUrl(Url url) {
        if (Objects.isNull(url)) {
            return;
        }
        getUrlMap().setMaxSize(50, EvictionMode.LRU);
        getUrlMap().put(url.getShortUrl(), url, 48, TimeUnit.HOURS);
    }

    public Url getLongUrl(String shortUrlSlug) {
        return getUrlMap().get(shortUrlSlug);
    }

    private RMapCache<String, Url> getUrlMap() {
        var bookCache = String.join(".", "ai.service.url.shortener");
        return this.redissonClient.getMapCache(bookCache);
    }
}
