package com.efederation.DTO;

import lombok.Data;

@Data
public class AddCharacterToShowRequest {
    private long character_id;
    private long show_id;
    private boolean npc;
}
