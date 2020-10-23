package com.example.shortening.dto;

import com.example.shortening.model.UrlEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto implements Serializable {

    private long id;

    private String fullUrl;
    private String shortUrl;
    private int visitcount;
    private boolean favourite;

    public UrlDto(UrlEntity urlEntity) {
        this.id = urlEntity.getId();
        this.fullUrl = urlEntity.getFullUrl();
        this.shortUrl = urlEntity.getShortUrl();
        this.visitcount = urlEntity.getVisitcount();
        this.favourite = urlEntity.getFavourite();
    }
}
