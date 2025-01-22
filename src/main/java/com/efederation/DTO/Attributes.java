package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Attributes {
    private int strength;
    private int speed;
}
