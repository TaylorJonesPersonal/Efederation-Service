package com.efederation.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateShowRequest {
    private String name;
    private String importance;
    private MultipartFile image;
}
