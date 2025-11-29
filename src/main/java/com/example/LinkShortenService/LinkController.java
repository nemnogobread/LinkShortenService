package com.example.LinkShortenService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/link")
    public ResponseEntity<String> getShortenLink(
            @RequestBody LinkRequest linkRequest
    ) {
        Optional<String> responseBody = linkService.getShortenLink(linkRequest.getLink());
        return responseBody
                .map(s -> ResponseEntity
                        .status(HttpStatus.PERMANENT_REDIRECT)
                        .body(s))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @PostMapping("/link")
    public ResponseEntity<String> createShortenLink(
            @RequestBody LinkRequest linkRequest
    ) {
        String responseBody = linkService.createShortenLink(linkRequest.getLink());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
