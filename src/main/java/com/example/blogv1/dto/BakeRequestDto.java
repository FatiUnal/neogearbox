package com.example.blogv1.dto;

public class BakeRequestDto extends PostRequestDto{

    private String portion;
    private boolean animalProduct;
    private String shelfLife;
    private String netQuantity;

    public BakeRequestDto(String title, String content, String portion, boolean animalProduct, String shelfLife, String netQuantity) {
        super(title, content);
        this.portion = portion;
        this.animalProduct = animalProduct;
        this.shelfLife = shelfLife;
        this.netQuantity = netQuantity;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public boolean isAnimalProduct() {
        return animalProduct;
    }

    public void setAnimalProduct(boolean animalProduct) {
        this.animalProduct = animalProduct;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getNetQuantity() {
        return netQuantity;
    }

    public void setNetQuantity(String netQuantity) {
        this.netQuantity = netQuantity;
    }
}
