package com.efederation.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class Wrestler extends Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToMany(mappedBy = "human_participants")
    private Set<Match> matches;

    @ManyToMany(mappedBy = "wrestlersContracted")
    private Set<Show> showsInvolvedIn = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="imageSetId", referencedColumnName = "id")
    private ImageSet imageSet;

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public Wrestler(String announceName, String firstName, String lastName, WrestlerAttributes wrestlerAttributes,
                    ImageSet imageSet, User user, Set<Match> matches)
    {
        super(announceName, firstName, lastName, wrestlerAttributes);
        this.user = user;
        this.matches = matches;
        this.imageSet = imageSet;
    }

    public boolean equals(Object object) {
        boolean result = false;
        if(object == null || object.getClass() != getClass()) {
            return result;
        }
        Wrestler comparison = (Wrestler) object;
        if(comparison.getId() == this.getId()
                && Objects.equals(comparison.getAnnounceName(), this.getAnnounceName())
                && Objects.equals(comparison.getLastName(), this.getLastName())
        ) {
            result = true;
        }
        return result;
    }

}
