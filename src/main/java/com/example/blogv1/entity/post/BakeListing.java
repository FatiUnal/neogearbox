package com.example.blogv1.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bake")
public class BakeListing extends PostDetails{

    private String portion;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean animalProduct;
    private String shelfLife;
    private String netQuantity;


    public BakeListing(String categoryName, String portion, boolean animalProduct, String shelfLife, String netQuantity) {
        super(categoryName);
        this.portion = portion;
        this.animalProduct = animalProduct;
        this.shelfLife = shelfLife;
        this.netQuantity = netQuantity;
    }

    public BakeListing() {

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
