package com.example.blogv1.entity.post;

import jakarta.persistence.*;

@Entity
@Table(name = "kw")
public class KwListing extends PostDetails{

    public KwListing(Category category) {
        super(category);
    }

    public KwListing() {

    }
}
