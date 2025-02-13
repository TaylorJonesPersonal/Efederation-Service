package com.efederation.DTO;

import com.efederation.Enums.Targets;
import lombok.Data;

@Data
public  class DrawnEvent {
    private String name;
    private Targets[] eventTargets;
    private String event;

    public DrawnEvent(String name, Targets[] eventTargets, String event) {
        this.name = name;
        this.eventTargets = eventTargets;
        this.event = event;
    }
}
