package com.example.blogv1.entity.post;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Çünkü bu sınıf, diğer varlık sınıflar tarafından miras alınacak.
public abstract class PostDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public PostDetails() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}