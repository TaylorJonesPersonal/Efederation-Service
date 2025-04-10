package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowResponse {
    private long id;
    private String name;
    private String importance;
    private String default_image;
    private String logo;
}
