package com.efederation.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchAttributesResponse {
    private long matchId;
    private MatchAttributes matchAttributes;
}

