package com.masonsmith.smithwedding.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Entity
public class Image {

    /**
     * Variables
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private OffsetDateTime uploadDate;

    /**
     * Constructors
     */

    public Image() {}

    public Image(String name) {
        this.name = name;
    }

    /**
     * Getters and Setters
     */

    public long getId() {
        return id;
    }

    public Image setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Image setName(String name) {
        this.name = name;
        return this;
    }

    public OffsetDateTime getUploadDate() {
        return uploadDate;
    }

    public Image setUploadDate(OffsetDateTime uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }
}
