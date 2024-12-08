package com.example.blogv1.entity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "img")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String filename;
    private int orders;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    public Image(String filename, int orders,ImageType type) {
        this.filename = filename;
        this.orders = orders;
        this.type = type;
    }

    public Image(String filename, Post post, ImageType type) {
        this.filename = filename;
        this.post = post;
        this.type = type;
    }

    public Image(String filename,ImageType type) {
        this.filename = filename;
        this.type = type;
    }

    public Image(String filename, int order, Post post) {
        this.filename = filename;
        this.orders = order;
        this.post = post;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getOrder() {
        return orders;
    }

    public void setOrder(int orders) {
        this.orders = orders;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }
}
