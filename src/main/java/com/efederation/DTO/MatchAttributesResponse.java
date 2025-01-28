package com.efederation.DTO;

import lombok.Data;

@Data
public class MatchAttributesResponse {
    private String wrestlerName;
    private String npcName;
    private String winner;
    private String created_at;
}
