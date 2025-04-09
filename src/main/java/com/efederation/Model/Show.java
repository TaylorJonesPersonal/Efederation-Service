package com.efederation.Model;

import com.efederation.Enums.Importance;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Lob
    @Column(name = "default_image")
    private byte[] defaultImage;

    @ManyToMany
    @JoinTable(
            name = "wrestler_show",
            joinColumns = @JoinColumn(name="wrestler_id"),
            inverseJoinColumns = @JoinColumn(name="show_id")
    )
    private Set<Wrestler> wrestlersContracted = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="npc_show",
            joinColumns = @JoinColumn(name="show_id"),
            inverseJoinColumns = @JoinColumn(name="npc_id")
    )
    private Set<NPC> npcsContracted = new HashSet<>();


    @Builder
    public Show(String name, Importance importance, byte[] defaultImage) {
        this.name = name;
        this.importance = importance;
        this.defaultImage = defaultImage;
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
