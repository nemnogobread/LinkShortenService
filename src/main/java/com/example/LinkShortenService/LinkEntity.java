package com.example.LinkShortenService;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String originalLink;

    @Column(unique = true, nullable = false)
    private String shortenLink;

    public LinkEntity(String originalLink, String shortenLink) {
        this.originalLink = originalLink;
        this.shortenLink = shortenLink;
    }
}
