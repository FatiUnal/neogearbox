package com.example.blogv1.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Çünkü bu sınıf, diğer varlık sınıflar tarafından miras alınacak.
public abstract class PostDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ilanNo;
    private String address;
    private EstateType estate;

    public PostDetails(String address, EstateType estate) {
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}