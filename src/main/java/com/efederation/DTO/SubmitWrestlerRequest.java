package com.efederation.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitWrestlerRequest {
    private String announceName;
    private String weapon;
    private String finishingMove;
}
