package com.example.blogv1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_post")
public class OrderPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderId;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public OrderPost(int id, int orderId, Post post) {
        this.id = id;
        this.orderId = orderId;
        this.post = post;
    }

    public OrderPost(int orderId, Post post) {
        this.orderId = orderId;
        this.post = post;
    }

    public OrderPost(int orderId) {
        this.orderId = orderId;
    }

    public OrderPost() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return orderId;
    }

    public void setOrder(int orderId) {
        this.orderId = orderId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
