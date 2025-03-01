package com.efederation.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
/**
 * Represents a character entity's set of images represented in db blob form.
 */
@Entity
@Data
public class ImageSet {

    public ImageSet () {
        super();
    }

    @Builder
    public ImageSet(String setName, byte[] idleImage, byte[] defeatedImage, byte[] attackFrame1,
                    byte [] attackFrame2, byte[] attackFrame3, byte[] attackFrame4) {
        this.setName = setName;
        this.idleImage = idleImage;
        this.defeatedImage = defeatedImage;
        this.attackFrame1 = attackFrame1;
        this.attackFrame2 = attackFrame2;
        this.attackFrame3 = attackFrame3;
        this.attackFrame4 = attackFrame4;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String setName;

    @Lob
    @giColumn(name = "idleImage")
    private byte[] idleImage;

    @Lob
    @Column(name = "defeatedImage")
    private byte[] defeatedImage;

    @Lob
    @Column(name = "attackFrame1")
    private byte[] attackFrame1;

    @Lob
    @Column(name = "attackFrame2")
    private byte[] attackFrame2;

    @Lob
    @Column(name = "attackFrame3")
    private byte[] attackFrame3;

    @Lob
    @Column(name = "attackFrame4")
    private byte[] attackFrame4;

}
