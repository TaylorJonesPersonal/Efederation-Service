package com.efederation.Model;

import com.efederation.Enums.Day;
import com.efederation.Enums.Importance;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Lob
    @Column(name = "default_image")
    private byte[] defaultImage;

    @Lob
    @Column(name = "logo_image")
    private byte[] logoImage;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "wrestler_show",
            joinColumns = @JoinColumn(name="show_id"),
            inverseJoinColumns = @JoinColumn(name="wrestler_id")
    )
    private Set<Wrestler> wrestlersContracted = new HashSet<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name="npc_show",
            joinColumns = @JoinColumn(name="show_id"),
            inverseJoinColumns = @JoinColumn(name="npc_id")
    )
    private Set<NPC> npcsContracted = new HashSet<>();


    @Builder
    public Show(String name, Day day, Importance importance, byte[] defaultImage, byte[] logoImage) {
        this.name = name;
        this.day = day;
        this.importance = importance;
        this.defaultImage = defaultImage;
        this.logoImage = logoImage;
    }

    public Show() {
        super();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Day getDay() {return this.day; }

    public void setDay(Day day) {
        this.day = day;
    }

    public Importance getImportance() {
        return this.importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public byte[] getDefaultImage() {
        return this.defaultImage;
    }

    public void setDefaultImage(byte[] defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getLogoImage() {return this.logoImage;}

    public void setLogoImage(byte[] logoImage) {this.logoImage = logoImage;}

    public Set<Wrestler> getWrestlersContracted() {
        return this.wrestlersContracted;
    }

    public void setWrestlersContracted(Set<Wrestler> wrestlersContracted) {
        this.wrestlersContracted = wrestlersContracted;
    }

    public Set<NPC> getNpcsContracted() {
        return this.npcsContracted;
    }

    public void setNpcsContracted(Set<NPC> npcsContracted) {
        this.npcsContracted = npcsContracted;
    }
}
