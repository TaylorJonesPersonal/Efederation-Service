package com.efederation.Model;

import com.efederation.Enums.GenderIdentity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrestlerAttributes {
    private String weapon;
    private String finishingMove;
    private GenderIdentity genderIdentity;

    @Min(1)
    @Max(99)
    @JsonProperty(required = true)
    private int strength = 0;

    @Min(1)
    @Max(99)
    @JsonProperty(required = true)
    private int speed = 0;

    public WrestlerAttributes(String weapon, String finishingMove, GenderIdentity genderIdentity) {
        this.weapon = weapon;
        this.finishingMove = finishingMove;
        this.genderIdentity = genderIdentity;
    }
}
