package com.example.shortening.repository;

import com.example.shortening.model.UrlEntity;
import com.example.shortening.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository <UrlEntity, Long> {
    public Optional<UrlEntity> findByShortUrl(String shortUrl);
    public Optional<UrlEntity> findById(Long id);
    public Optional<List<UrlEntity>> findAllByUserAndFavourite(UserEntity user, boolean favourite);
}





