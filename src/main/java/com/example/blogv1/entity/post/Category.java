package com.example.blogv1.entity.post;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private Image coverImage;

    private String linkName;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    @PrePersist
    @PreUpdate
    public void generateProductData() {
        if (name != null) {
            String processedName = name.trim().toLowerCase();

            processedName = processedName
                    .replace("ç", "c")
                    .replace("ğ", "g")
                    .replace("ı", "i")
                    .replace("ö", "o")
                    .replace("ş", "s")
                    .replace("ü", "u");

            this.linkName = processedName.replaceAll("\\s+", "-");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
}
