package com.efederation.DTO;

import com.efederation.Enums.ImageType;
import lombok.Data;

@Data
public class WrestlerImageCreateRequest {
    private long id;
    private String imageBase64;
    private ImageType type;
}
