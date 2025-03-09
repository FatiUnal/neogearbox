package com.example.blogv1.entity.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bake")
public class BakeListing extends PostDetails{

    private String bake;

    public BakeListing(String bake) {
        this.bake = bake;
    }


    public BakeListing() {

    }

    public String getBake() {
        return bake;
    }

    public void setBake(String bake) {
        this.bake = bake;
    }
}
