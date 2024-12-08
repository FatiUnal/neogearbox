package com.example.blogv1.dto;

public class HouseRequestDto extends PostRequestDto{

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

    public HouseRequestDto(String title, String content, String type,String address, int brutMetrekare, int netMetrekare, String odaSayisi, String binaYasi, int bulunduguKat, int katSayisi, String isitma, int banyoSayisi, String mutfakTipi, boolean balkon, boolean asansor, boolean otopark, boolean esyali, String kullanimDurumu, boolean siteIcerisinde, String siteAdi, String aidat, boolean krediyeUygun, String tapuDurumu, boolean takas) {
        super(title, content,address,type);
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

    public int getBrutMetrekare() {
        return brutMetrekare;
    }

    public int getNetMetrekare() {
        return netMetrekare;
    }

    public String getOdaSayisi() {
        return odaSayisi;
    }

    public String getBinaYasi() {
        return binaYasi;
    }

    public int getBulunduguKat() {
        return bulunduguKat;
    }

    public int getKatSayisi() {
        return katSayisi;
    }

    public String getIsitma() {
        return isitma;
    }

    public int getBanyoSayisi() {
        return banyoSayisi;
    }

    public String getMutfakTipi() {
        return mutfakTipi;
    }

    public boolean isBalkon() {
        return balkon;
    }

    public boolean isAsansor() {
        return asansor;
    }

    public boolean isOtopark() {
        return otopark;
    }

    public boolean isEsyali() {
        return esyali;
    }

    public String getKullanimDurumu() {
        return kullanimDurumu;
    }

    public boolean isSiteIcerisinde() {
        return siteIcerisinde;
    }

    public String getSiteAdi() {
        return siteAdi;
    }

    public String getAidat() {
        return aidat;
    }

    public boolean isKrediyeUygun() {
        return krediyeUygun;
    }

    public String getTapuDurumu() {
        return tapuDurumu;
    }

    public boolean isTakas() {
        return takas;
    }
}
