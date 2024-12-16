package com.example.blogv1.dto;

public class HouseRequestDto extends PostRequestDto{
    private String price;
    private String brutMetrekare;
    private String isitma;
    private String odaSayisi;
    private boolean mutfakTipi;
    private boolean otopark;
    private boolean havuz;
    private boolean oyunPark;
    private boolean güvenlik;
    private boolean sporSalon;

    private String context1;
    private String context2;
    private String context3;

    public HouseRequestDto(String sehir,String ilce,String title, String content, String type, String price, String brutMetrekare, String isitma, String odaSayisi, boolean mutfakTipi, boolean otopark, boolean havuz, boolean oyunPark, boolean güvenlik, boolean sporSalon, String context1, String context2, String context3) {
        super(title, content, type,sehir,ilce);
        this.price = price;
        this.brutMetrekare = brutMetrekare;
        this.isitma = isitma;
        this.odaSayisi = odaSayisi;
        this.mutfakTipi = mutfakTipi;
        this.otopark = otopark;
        this.havuz = havuz;
        this.oyunPark = oyunPark;
        this.güvenlik = güvenlik;
        this.sporSalon = sporSalon;
        this.context1 = context1;
        this.context2 = context2;
        this.context3 = context3;
    }

    public String getBrutMetrekare() {
        return brutMetrekare;
    }

    public void setBrutMetrekare(String brutMetrekare) {
        this.brutMetrekare = brutMetrekare;
    }

    public String getIsitma() {
        return isitma;
    }

    public void setIsitma(String isitma) {
        this.isitma = isitma;
    }

    public String getOdaSayisi() {
        return odaSayisi;
    }

    public void setOdaSayisi(String odaSayisi) {
        this.odaSayisi = odaSayisi;
    }

    public boolean isMutfakTipi() {
        return mutfakTipi;
    }

    public void setMutfakTipi(boolean mutfakTipi) {
        this.mutfakTipi = mutfakTipi;
    }

    public boolean isOtopark() {
        return otopark;
    }

    public void setOtopark(boolean otopark) {
        this.otopark = otopark;
    }

    public boolean isHavuz() {
        return havuz;
    }

    public void setHavuz(boolean havuz) {
        this.havuz = havuz;
    }

    public boolean isOyunPark() {
        return oyunPark;
    }

    public void setOyunPark(boolean oyunPark) {
        this.oyunPark = oyunPark;
    }

    public boolean isGüvenlik() {
        return güvenlik;
    }

    public void setGüvenlik(boolean güvenlik) {
        this.güvenlik = güvenlik;
    }

    public boolean isSporSalon() {
        return sporSalon;
    }

    public void setSporSalon(boolean sporSalon) {
        this.sporSalon = sporSalon;
    }

    public String getContext1() {
        return context1;
    }

    public void setContext1(String context1) {
        this.context1 = context1;
    }

    public String getContext2() {
        return context2;
    }

    public void setContext2(String context2) {
        this.context2 = context2;
    }

    public String getContext3() {
        return context3;
    }

    public void setContext3(String context3) {
        this.context3 = context3;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
