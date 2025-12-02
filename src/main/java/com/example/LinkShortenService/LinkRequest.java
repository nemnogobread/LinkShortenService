package com.example.LinkShortenService;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LinkRequest {
    @NotBlank
    private String link;
}
