package com.efederation.Constants;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MatchConstants {
    private final String[] moves = {
            "Snap Suplex", "Snapmare", "Corner Running Splash", "Dropkick", "Running Lariat",
    "Powerbomb", "Scoop Slam", "Running Knee"
    };
}
