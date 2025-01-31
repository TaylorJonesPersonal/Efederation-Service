package com.efederation.Model;

import com.efederation.Converters.HashMapConverter;
import jakarta.persistence.*;
import lombok.*;
import java.util.Random;

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

    @Lob
    @Column(name="defeatedImage")
    private byte[] defeatedImage;

    public Integer fight() {
        Random roll = new Random();
        return this.wrestlerAttributes.getSpeed() + this.wrestlerAttributes.getStrength() + roll.nextInt(20);
    }

}
