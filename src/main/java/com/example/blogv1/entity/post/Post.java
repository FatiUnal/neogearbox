package com.example.blogv1.entity.post;

import com.example.blogv1.entity.Admin;
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
    private String titleEng;
    private String titleContent;
    private String titleContentEng;
    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private String contentEng;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private Image coverImage;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Image> images;

    @OneToOne(cascade = CascadeType.ALL)
    private Image pdf;

    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)  // orphanRemoval, bağlı olmayan detayları siler
    @JoinColumn(name = "post_details_id", referencedColumnName = "id", nullable = false)
    private PostDetails postDetails; // Polimorfik detaylar


    public Post() {
        this.createdAt = LocalDateTime.now();
    }
    public Post(String title, String titleEng, String titleContent, String titleContentEng, String content, String contentEng, PostDetails postDetails) {
        this.title = title;
        this.titleEng = titleEng;
        this.titleContent = titleContent;
        this.titleContentEng = titleContentEng;
        this.content = content;
        this.contentEng = contentEng;
        this.postDetails = postDetails;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(PostDetails postDetails) {
        this.postDetails = postDetails;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public Image getPdf() {
        return pdf;
    }

    public void setPdf(Image pdf) {
        this.pdf = pdf;
    }

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getTitleContentEng() {
        return titleContentEng;
    }

    public void setTitleContentEng(String titleContentEng) {
        this.titleContentEng = titleContentEng;
    }

    public String getContentEng() {
        return contentEng;
    }

    public void setContentEng(String contentEng) {
        this.contentEng = contentEng;
    }
}
