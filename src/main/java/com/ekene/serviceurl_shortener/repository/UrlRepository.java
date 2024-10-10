package com.ekene.serviceurl_shortener.repository;

import com.ekene.serviceurl_shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);
    Url findByLongUrl(String longUrl);
}
