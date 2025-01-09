package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMatchResponse {
    private String winnerName;
}
