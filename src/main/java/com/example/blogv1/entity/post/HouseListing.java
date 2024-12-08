package com.example.blogv1.entity.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "house")
public class HouseListing extends PostDetails{

    private int brutMetrekare;
    private int netMetrekare;
    private String odaSayisi;
    private String binaYasi;
    private int bulunduguKat;
    private int katSayisi;
    private String isitma;
    private int banyoSayisi;
    private String mutfakTipi;
    private boolean balkon;
    private boolean asansor;
    private boolean otopark;
    private boolean esyali;
    private String kullanimDurumu;
    private boolean siteIcerisinde;
    private String siteAdi;
    private String aidat;
    private boolean krediyeUygun;
    private String tapuDurumu;
    private boolean takas;


    public HouseListing(String address, int brutMetrekare, int netMetrekare, String odaSayisi, String binaYasi, int bulunduguKat, int katSayisi, String isitma, int banyoSayisi, String mutfakTipi, boolean balkon, boolean asansor, boolean otopark, boolean esyali, String kullanimDurumu, boolean siteIcerisinde, String siteAdi, String aidat, boolean krediyeUygun, String tapuDurumu, boolean takas) {
        super(address, EstateType.HOUSE);
        this.brutMetrekare = brutMetrekare;
        this.netMetrekare = netMetrekare;
        this.odaSayisi = odaSayisi;
        this.binaYasi = binaYasi;
        this.bulunduguKat = bulunduguKat;
        this.katSayisi = katSayisi;
        this.isitma = isitma;
        this.banyoSayisi = banyoSayisi;
        this.mutfakTipi = mutfakTipi;
        this.balkon = balkon;
        this.asansor = asansor;
        this.otopark = otopark;
        this.esyali = esyali;
        this.kullanimDurumu = kullanimDurumu;
        this.siteIcerisinde = siteIcerisinde;
        this.siteAdi = siteAdi;
        this.aidat = aidat;
        this.krediyeUygun = krediyeUygun;
        this.tapuDurumu = tapuDurumu;
        this.takas = takas;
    }

    public HouseListing() {
        super();
    }

    public int getBrutMetrekare() {
        return brutMetrekare;
    }

    public void setBrutMetrekare(int brutMetrekare) {
        this.brutMetrekare = brutMetrekare;
    }

    public String getAidat() {
        return aidat;
    }

    public void setAidat(String aidat) {
        this.aidat = aidat;
    }

    public int getNetMetrekare() {
        return netMetrekare;
    }

    public void setNetMetrekare(int netMetrekare) {
        this.netMetrekare = netMetrekare;
    }

    public String getOdaSayisi() {
        return odaSayisi;
    }

    public void setOdaSayisi(String odaSayisi) {
        this.odaSayisi = odaSayisi;
    }

    public String getBinaYasi() {
        return binaYasi;
    }

    public void setBinaYasi(String binaYasi) {
        this.binaYasi = binaYasi;
    }

    public int getBulunduguKat() {
        return bulunduguKat;
    }

    public void setBulunduguKat(int bulunduguKat) {
        this.bulunduguKat = bulunduguKat;
    }

    public int getKatSayisi() {
        return katSayisi;
    }

    public void setKatSayisi(int katSayisi) {
        this.katSayisi = katSayisi;
    }

    public String getIsitma() {
        return isitma;
    }

    public void setIsitma(String isitma) {
        this.isitma = isitma;
    }

    public int getBanyoSayisi() {
        return banyoSayisi;
    }

    public void setBanyoSayisi(int banyoSayisi) {
        this.banyoSayisi = banyoSayisi;
    }

    public String getMutfakTipi() {
        return mutfakTipi;
    }

    public void setMutfakTipi(String mutfakTipi) {
        this.mutfakTipi = mutfakTipi;
    }

    public boolean isBalkon() {
        return balkon;
    }

    public void setBalkon(boolean balkon) {
        this.balkon = balkon;
    }

    public boolean isAsansor() {
        return asansor;
    }

    public void setAsansor(boolean asansor) {
        this.asansor = asansor;
    }

    public boolean isOtopark() {
        return otopark;
    }

    public void setOtopark(boolean otopark) {
        this.otopark = otopark;
    }

    public boolean isEsyali() {
        return esyali;
    }

    public void setEsyali(boolean esyali) {
        this.esyali = esyali;
    }

    public String getKullanimDurumu() {
        return kullanimDurumu;
    }

    public void setKullanimDurumu(String kullanimDurumu) {
        this.kullanimDurumu = kullanimDurumu;
    }

    public boolean isSiteIcerisinde() {
        return siteIcerisinde;
    }

    public void setSiteIcerisinde(boolean siteIcerisinde) {
        this.siteIcerisinde = siteIcerisinde;
    }

    public String getSiteAdi() {
        return siteAdi;
    }

    public void setSiteAdi(String siteAdi) {
        this.siteAdi = siteAdi;
    }

    public boolean isKrediyeUygun() {
        return krediyeUygun;
    }

    public void setKrediyeUygun(boolean krediyeUygun) {
        this.krediyeUygun = krediyeUygun;
    }

    public String getTapuDurumu() {
        return tapuDurumu;
    }

    public void setTapuDurumu(String tapuDurumu) {
        this.tapuDurumu = tapuDurumu;
    }

    public boolean isTakas() {
        return takas;
    }

    public void setTakas(boolean takas) {
        this.takas = takas;
    }
}
