package com.example.blogv1.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Çünkü bu sınıf, diğer varlık sınıflar tarafından miras alınacak.
public abstract class PostDetails {
    @Id
    @GeneratedValue
    private int id;
    private String ilanNo;
    private String adres;
    private EstateType estate;

    public PostDetails(String adres, EstateType estate) {
        this.adres = adres;
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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}