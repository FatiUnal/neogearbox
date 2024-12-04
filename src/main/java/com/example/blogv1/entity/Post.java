package com.example.blogv1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private String coverImage;
    @ElementCollection
    private List<String> images;
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonIgnore
    private Admin admin;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)  // orphanRemoval, bağlı olmayan detayları siler
    @JoinColumn(name = "post_details_id", referencedColumnName = "id", nullable = false)
    private PostDetails postDetails; // Polimorfik detaylar


    public Post() {
        this.status = PostStatus.INACTIVE;
        this.createdAt = LocalDateTime.now();
    }
    public Post(String title, String content, Admin admin, PostDetails postDetails) {
        this.title = title;
        this.content = content;
        this.admin = admin;
        this.postDetails = postDetails;
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

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(PostDetails postDetails) {
        this.postDetails = postDetails;
    }
}
