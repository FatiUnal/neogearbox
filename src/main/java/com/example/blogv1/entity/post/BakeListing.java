package com.example.blogv1.entity.post;

import jakarta.persistence.*;

@Entity
@Table(name = "bake")
public class BakeListing extends PostDetails{

    public BakeListing(String categoryName) {
        super(categoryName);
    }

    public BakeListing() {

    }
}
