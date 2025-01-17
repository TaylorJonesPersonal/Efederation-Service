package com.efederation.DTO;

import com.efederation.Model.WrestlerAttributes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NPCResponse {
    private long wrestlerId;
    private String announceName;
    private WrestlerAttributes attributes;
    private String image;
    private String weightClass;
}
