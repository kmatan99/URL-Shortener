package com.example.shortening.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullUrl;
    private String shortUrl;
    private int visitcount;
    private Boolean favourite;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void increaseVisitcount() {
        this.visitcount = visitcount + 1;
    }

    public UrlEntity(String fullUrl, String shortUrl, UserEntity user, Boolean favourite) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.user = user;
        this.favourite = favourite;
    }

    public UrlEntity() {
    }
}
