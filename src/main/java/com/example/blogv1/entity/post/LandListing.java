package com.example.blogv1.entity.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "land")
public class LandListing extends PostDetails{

    private int brutMetrekare;
    private int netMetrekare;
    private String imarDurumu;
    private String adaParsel;
    private String tapuDurumu;

    public LandListing(String sehir,String ilce,String price,int brutMetrekare, int netMetrekare, String imarDurumu, String adaParsel, String tapuDurumu) {
        super(EstateType.LAND,price,sehir,ilce);
        this.brutMetrekare = brutMetrekare;
        this.netMetrekare = netMetrekare;
        this.imarDurumu = imarDurumu;
        this.adaParsel = adaParsel;
        this.tapuDurumu = tapuDurumu;
    }

    public LandListing() {
        super();

    }

    public int getBrutMetrekare() {
        return brutMetrekare;
    }

    public void setBrutMetrekare(int brutMetrekare) {
        this.brutMetrekare = brutMetrekare;
    }

    public int getNetMetrekare() {
        return netMetrekare;
    }

    public void setNetMetrekare(int netMetrekare) {
        this.netMetrekare = netMetrekare;
    }

    public String getImarDurumu() {
        return imarDurumu;
    }

    public void setImarDurumu(String imarDurumu) {
        this.imarDurumu = imarDurumu;
    }

    public String getAdaParsel() {
        return adaParsel;
    }

    public void setAdaParsel(String adaParsel) {
        this.adaParsel = adaParsel;
    }

    public String getTapuDurumu() {
        return tapuDurumu;
    }

    public void setTapuDurumu(String tapuDurumu) {
        this.tapuDurumu = tapuDurumu;
    }
}
