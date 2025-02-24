package com.efederation.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@MappedSuperclass
@EqualsAndHashCode
@Builder
public class Event {
    private String name;
    private String description;

    @Lob
    @Column(name = "imagedata")
    @Nullable
    private byte[] imageData;

    public Event() {
    }

    public Event(String name, String description, @Nullable byte[] imageData) {
        this.name = name;
        this.description = description;
        this.imageData = imageData;
    }

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this. name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageData() {
        return this.imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String toString() {
        return "Event{"
                + "name='" + this.name + '\''
                + ", description='" + this.description + '\''
                + ", imageData='" + Arrays.toString(this.imageData) + '\'' + '}';
    }
}
