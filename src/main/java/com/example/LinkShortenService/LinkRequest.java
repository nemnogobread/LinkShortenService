package com.example.LinkShortenService;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.net.URL;

@Data
public class LinkRequest {
    @NotBlank
    private String link;
}
