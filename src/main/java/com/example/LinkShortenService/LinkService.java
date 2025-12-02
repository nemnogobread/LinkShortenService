package com.example.LinkShortenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private static final int GENERATE_ATTEMPTS = 10;
    private static final int SHORTEN_LINK_LENGTH = 20;

    private final Random random = new Random();

    public Optional<String> getOriginalLink(String shortenLink) {
        log.debug("Looking up original link for short link: {}", shortenLink);
        Optional<LinkEntity> entity = linkRepository.findByShortenLink(shortenLink);

        if (entity.isPresent()) {
            log.debug("Found original link for {}", shortenLink);
        } else {
            log.debug("No original link found for {}", shortenLink);
        }

        return entity.map(LinkEntity::getOriginalLink);
    }

    public String createShortenLink(String originalLink) {
        log.debug("Starting short link generation for: {}", originalLink);
        String shortenLink = null;

        for (int i = 0; i < GENERATE_ATTEMPTS; i++) {
            String candidate = generateShortenLink();
            log.debug("Attempt {}: generated candidate {}", i + 1, candidate);

            if (linkRepository.findByShortenLink(candidate).isEmpty()) {
                shortenLink = candidate;
                log.debug("Unique short link found: {}", shortenLink);
                break;
            } else {
                log.debug("Collision detected for candidate: {}", candidate);
            }
        }

        if (shortenLink == null) {
            log.error("Failed to generate unique short link after {} attempts for URL: {}",
                    GENERATE_ATTEMPTS, originalLink);
            throw new RuntimeException("Failed to generate unique short link after "
                    + GENERATE_ATTEMPTS + " attempts");
        }

        LinkEntity linkEntity = new LinkEntity(originalLink, shortenLink);
        linkRepository.save(linkEntity);
        log.info("Saved mapping: {} -> {}", shortenLink, originalLink);

        return shortenLink;
    }

    private String generateShortenLink() {
        String base62Chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortenLink = new StringBuilder(SHORTEN_LINK_LENGTH);

        for (int i = 0; i < SHORTEN_LINK_LENGTH; i++) {
            int randomIndex = random.nextInt(base62Chars.length());
            shortenLink.append(base62Chars.charAt(randomIndex));
        }

        return shortenLink.toString();
    }
}
