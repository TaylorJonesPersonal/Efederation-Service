package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="NPC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NPC extends Character{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long npc_id;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="npc_id"),
            inverseJoinColumns = @JoinColumn(name="match_id")
    )
    private Set<Match> matches;

    @Builder
    public NPC(String announceName, String firstName, String lastName, WrestlerAttributes wrestlerAttributes, byte[] imageData) {
        super(announceName, firstName, lastName, wrestlerAttributes, imageData);
    }
}
