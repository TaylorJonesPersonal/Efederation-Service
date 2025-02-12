package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="WRESTLERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wrestler extends Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wrestler_id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="wrestler_id"),
            inverseJoinColumns = @JoinColumn(name="matchId")
    )
    private Set<Match> matches;

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public Wrestler(String announceName, String firstName, String lastName, WrestlerAttributes wrestlerAttributes,
                    byte[] imageData, byte[] defeatedImage, User user, Set<Match> matches)
    {
        super(announceName, firstName, lastName, wrestlerAttributes, imageData, defeatedImage);
        this.user = user;
        this.matches = matches;
    }

}
