package com.example.LinkShortenService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{shortenLink}")
    public ResponseEntity<Object> redirect(
            @PathVariable String shortenLink
    ) {
        log.info("Redirect request received for short link: {}", shortenLink);
        Optional<String> redirectLocation = linkService.getOriginalLink(shortenLink);
        return redirectLocation
                .map(s -> {
                    log.info("Redirecting {} to {}", shortenLink, s);
                    return ResponseEntity
                            .status(HttpStatus.FOUND)
                            .location(URI.create(s))
                            .build();
                })
                .orElseGet(() -> {
                    log.warn("Short link not found: {}", shortenLink);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .build();
                });
    }

    @PostMapping("/link")
    public ResponseEntity<String> createShortenLink(
            @Valid @RequestBody LinkRequest linkRequest
    ) {
        log.info("Creating short link for original URL: {}", linkRequest.getLink());
        String responseBody = linkService.createShortenLink(linkRequest.getLink());
        log.info("Short link created successfully: {}", responseBody);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }
}
