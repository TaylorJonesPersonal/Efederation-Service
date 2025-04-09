package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToMany
    @JoinTable(
            name = "wrestler_match",
            joinColumns = @JoinColumn(name="match_id"),
            inverseJoinColumns = @JoinColumn(name="wrestler_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Wrestler> human_participants;

    @ManyToMany
    @JoinTable(
            name="npc_match",
            joinColumns = @JoinColumn(name="match_id"),
            inverseJoinColumns = @JoinColumn(name="npc_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<NPC> npc_participants;

    private String winner;

    private String condition;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<MatchEvent> matchEvents = new HashSet<>();

    @OneToMany(mappedBy="match", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Memory> matchMemories = new HashSet<>();

    @CreationTimestamp
    private Timestamp createdAt;

}
