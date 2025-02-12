package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MatchEvent extends Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long event_id;

    @ManyToOne
    @JoinColumn(name="matchId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Match match;

    public MatchEvent() {
        super();
    }

    @Builder
    public MatchEvent(String name, String description) {
        super(name, description);
    }
}
