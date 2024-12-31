package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;

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
    private User user;


    @Builder
    public Wrestler(String announceName, String firstName, String lastName, WrestlerAttributes wrestlerAttributes, byte[] imageData, User user) {
        super(announceName, firstName, lastName, wrestlerAttributes, imageData);
        this.user = user;
    }

}
