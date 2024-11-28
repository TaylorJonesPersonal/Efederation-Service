package com.efederation.Model;

import com.efederation.Converters.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="WRESTLERS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wrestler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wrestler_id;

    private String announceName;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Lob
    @Column(name="imagedata", length = 1000)
    private byte[] imageData;

    @Convert(converter = HashMapConverter.class)
    private WrestlerAttributes wrestlerAttributes;
}
