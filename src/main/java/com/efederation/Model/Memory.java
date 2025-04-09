package com.efederation.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Memory extends Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="match_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Match match;

    @CreationTimestamp
    private Timestamp createdAt;

}
