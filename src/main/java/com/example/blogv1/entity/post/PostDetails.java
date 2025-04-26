package com.example.blogv1.entity.post;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Çünkü bu sınıf, diğer varlık sınıflar tarafından miras alınacak.
public abstract class PostDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public PostDetails(Category category) {
        this.category = category;
    }

    public PostDetails() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}