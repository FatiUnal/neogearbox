package com.example.blogv1.entity.post;

import jakarta.persistence.*;

@Entity
@Table(name = "kw")
public class KwListing extends PostDetails{

    public KwListing(String categoryName) {
        super(categoryName);
    }

    public KwListing() {

    }
}
