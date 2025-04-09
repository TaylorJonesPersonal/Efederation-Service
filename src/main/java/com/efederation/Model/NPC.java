package com.efederation.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NPC extends Character{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(mappedBy = "npc_participants")
    private Set<Match> matches;

    @ManyToMany(mappedBy = "npcsContracted")
    private Set<Show> showsInvolvedIn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="imageSetId", referencedColumnName = "id")
    private ImageSet imageSet;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public NPC(String announceName, String firstName, String lastName, WrestlerAttributes wrestlerAttributes, ImageSet imageSet) {
        super(announceName, firstName, lastName, wrestlerAttributes);
        this.imageSet = imageSet;
    }
}
