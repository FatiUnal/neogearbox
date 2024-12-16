package com.example.blogv1.entity.post;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Çünkü bu sınıf, diğer varlık sınıflar tarafından miras alınacak.
public abstract class PostDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ilanNo;
    private EstateType estate;
    private String fiyat;
    private String sehir;
    private String ilce;

    public PostDetails(EstateType estate, String fiyat, String sehir, String ilce) {
        this.fiyat = fiyat;
        this.sehir = sehir;
        this.ilce = ilce;
        Random random = new Random();
        int fiveDigitNumber = 10000 + random.nextInt(90000);
        this.ilanNo = String.valueOf(fiveDigitNumber);
        this.estate = estate;
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

    public String getIlanNo() {
        return ilanNo;
    }

    public void setIlanNo(String ilanNo) {
        this.ilanNo = ilanNo;
    }

    public EstateType getEstate() {
        return estate;
    }

    public void setEstate(EstateType estate) {
        this.estate = estate;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getIlce() {
        return ilce;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }
}