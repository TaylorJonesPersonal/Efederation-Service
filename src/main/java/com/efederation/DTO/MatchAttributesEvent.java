package com.efederation.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchAttributesEvent {
private String name;
private String description;
}
