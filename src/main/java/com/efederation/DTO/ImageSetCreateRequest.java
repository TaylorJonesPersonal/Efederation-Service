package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ImageSetCreateRequest {
    private String name;
    private MultipartFile idle;
    private MultipartFile defeated;
    private MultipartFile attackOne;
    private MultipartFile attackTwo;
    private MultipartFile attackThree;
    private MultipartFile attackFour;
}
