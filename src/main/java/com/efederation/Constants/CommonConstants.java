package com.efederation.Constants;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CommonConstants {
    private final String fromEmail = "efedbusiness@gmail.com";
    private final String npcDenotation = "n";
    private final int LIGHT_HEAVYWEIGHT_MIN_WEIGHT = 225;
    private final int HEAVYWEIGHT_MIN_WEIGHT = 250;
    private final int SUPERHEAVYWEIGHT_MIN_WEIGHT = 325;
}
