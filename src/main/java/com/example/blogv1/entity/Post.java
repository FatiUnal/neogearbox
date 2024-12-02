package com.example.blogv1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private String coverImage;
    @ElementCollection
    private List<String> images;
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PostStatus status;


    public Post() {}
    public Post(String title, String content, Admin admin) {
        this.title = title;
        this.content = content;
        this.admin = admin;
        this.status = PostStatus.INACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
