package com.example.LinkShortenService;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String originalLink;

    @Column
    private String shortenLink;

    public LinkEntity(String originalLink, String shortenLink) {
        this.originalLink = originalLink;
        this.shortenLink = shortenLink;
    }
}
