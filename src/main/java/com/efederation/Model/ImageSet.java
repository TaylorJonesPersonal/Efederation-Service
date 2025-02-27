package com.efederation.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a character entity's set of images represented in db blob form.
 */
@Entity
@Getter
@Setter
public class ImageSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String setName;

    @Lob
    private byte[] idleImage;

    @Lob
    private byte[] defeatedImage;

    @Lob
    private byte[] attackFrame1;

    @Lob
    private byte[] attackFrame2;

    @Lob
    private byte[] attackFrame3;

    @Lob
    private byte[] attackFrame4;

    public ImageSet() {
        super();
    }

    @Builder
    public ImageSet(String setName, byte[] idleImage, byte[] defeatedImage, byte[] attackFrame1,
                    byte [] attackFrame2, byte[] attackFrame3, byte [] attackFrame4) {
        this.setName = setName;
        this.idleImage = idleImage;
        this.defeatedImage = defeatedImage;
        this.attackFrame1 = attackFrame1;
        this.attackFrame2 = attackFrame2;
        this.attackFrame3 = attackFrame3;
        this.attackFrame4 = attackFrame4;
    }
}
