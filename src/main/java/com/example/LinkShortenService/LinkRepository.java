package com.example.LinkShortenService;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {
    public Optional<LinkEntity> findByOriginalLink(String originalLink);
    public Optional<LinkEntity> findByShortenLink(String originalLink);
}
