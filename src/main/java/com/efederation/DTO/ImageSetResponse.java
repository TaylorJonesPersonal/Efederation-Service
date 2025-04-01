package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageSetResponse {
    private int id;
    private String base64Image;
}
