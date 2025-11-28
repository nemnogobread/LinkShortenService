package com.example.LinkShortenService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LinkController {

    @GetMapping("/link")
    public ResponseEntity<String> getShortenLink(){
        String responseBody = "Hello World";
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
