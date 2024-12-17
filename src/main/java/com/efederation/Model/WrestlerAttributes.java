package com.efederation.Model;

import com.efederation.Enums.GenderIdentity;
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

    public WrestlerAttributes(String genderIdentity) {
        this.genderIdentity = GenderIdentity.valueOf(genderIdentity);
    }
}
