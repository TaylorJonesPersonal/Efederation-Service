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
    private String firstName;
    private String lastName;
    private String genderIdentity;
    private String weapon;
    private String finishingMove;
}
