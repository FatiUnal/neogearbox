package com.example.blogv1.entity.post;

import jakarta.persistence.*;

@Entity
@Table(name = "bake")
public class BakeListing extends PostDetails{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private Image image;

    public BakeListing(String categoryName) {
        super(categoryName);
    }

    public BakeListing() {

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
