package com.example.blogv1.dto;

import com.example.blogv1.entity.PostDetails;

public class LandRequestDto extends PostRequestDto{

    private int brutMetrekare;
    private int netMetrekare;
    private String imarDurumu;
    private String adaParsel;
    private String tapuDurumu;

    public LandRequestDto(String title, String content, String type,String address, int brutMetrekare, int netMetrekare, String imarDurumu, String adaParsel, String tapuDurumu) {
        super(title, content,type,address);
        this.brutMetrekare = brutMetrekare;
        this.netMetrekare = netMetrekare;
        this.imarDurumu = imarDurumu;
        this.adaParsel = adaParsel;
        this.tapuDurumu = tapuDurumu;
    }

    public int getBrutMetrekare() {
        return brutMetrekare;
    }

    public int getNetMetrekare() {
        return netMetrekare;
    }

    public String getImarDurumu() {
        return imarDurumu;
    }

    public String getAdaParsel() {
        return adaParsel;
    }

    public String getTapuDurumu() {
        return tapuDurumu;
    }
}
