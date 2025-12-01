package com.example.LinkShortenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private static final int GENERATE_ATTEMPTS = 10;

    public Optional<String> getShortenLink(String originalLink) {
        return linkRepository.findByOriginalLink(originalLink)
                .map(LinkEntity::getShortenLink);
    }

    public String createShortenLink(String originalLink) {
        String shortenLink = null;

        for (int i = 0; i < GENERATE_ATTEMPTS; i++) {
            String candidate = generateShortenLink(originalLink);
            if (linkRepository.findByShortenLink(shortenLink).isEmpty()) {
                shortenLink = candidate;
                break;
            }
        }

        if (shortenLink == null) {
            throw new RuntimeException("Failed to generate unique short link after "
                    + GENERATE_ATTEMPTS + " attempts");
        }

        LinkEntity linkEntity = new LinkEntity(originalLink, shortenLink);
        linkRepository.save(linkEntity);
        return shortenLink;
    }

    // TODO: write generate algorithm
    private String generateShortenLink(String originalLink) {
        return originalLink;
    }
}
