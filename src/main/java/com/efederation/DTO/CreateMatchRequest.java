package com.efederation.DTO;

import lombok.Data;

@Data
public class CreateMatchRequest {
    private String[] participant_ids;
}
