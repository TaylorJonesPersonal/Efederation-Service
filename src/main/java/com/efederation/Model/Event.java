package com.efederation.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@MappedSuperclass
@EqualsAndHashCode
public abstract class Event {
    private String name;
    private String description;

    @Lob
    @Column(name = "imagedata")
    @Nullable
    private byte[] imageData;

    public Event(String name, String description, byte[] imageData) {
        this.name = name;
        this.description = description;
        this.imageData = imageData;
    }

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String getName() {
        return this.name;
    }

    private void setName(String name) {
        this. name = name;
    }

    private String getDescription() {
        return this.description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private byte[] getImageData() {
        return this.imageData;
    }

    private void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String toString() {
        return "Event{"
                + "name='" + this.name + '\''
                + ", description='" + this.description + '\''
                + ", imageData='" + Arrays.toString(this.imageData) + '\'' + '}';
    }
}
