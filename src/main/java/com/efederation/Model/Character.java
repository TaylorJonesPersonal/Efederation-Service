package com.efederation.Model;

import com.efederation.Converters.HashMapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
public abstract class Character {
    private String announceName;
    private String firstName;
    private String lastName;

    @Convert(converter = HashMapConverter.class)
    private WrestlerAttributes wrestlerAttributes;

    @Lob
    @Column(name="imagedata", length = 1000)
    private byte[] imageData;
}
