package com.efederation.DTO;

import lombok.Data;
import java.util.List;

@Data
public class MatchAttributes {
    private String created_at;
    private String participants;
    private String winner;
    private String condition;
    private String defeatedImage;
    private List<String> actionImages;
    private List<MatchAttributesResponseEvent> events;
    private List<MatchAttributesResponseEvent> memories;
}
