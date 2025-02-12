package com.efederation.DTO;

import com.efederation.Model.Character;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WinnersLosers {
    private Character winner;
    private Character loser;
}
