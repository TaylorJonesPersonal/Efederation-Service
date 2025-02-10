package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Match")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long match_id;

    @ManyToMany
    private Set<Wrestler> human_participants;

    @ManyToMany
    private Set<NPC> npc_participants;

    private String winner;

    private String condition;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<MatchEvent> matchEvents = new HashSet<>();

    @CreationTimestamp
    private Timestamp createdAt;

}
