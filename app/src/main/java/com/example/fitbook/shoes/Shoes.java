package com.example.fitbook.shoes;

public class Shoes {
    public String shoe_name;
    public String shoe_brand;
    public float shoe_distance;

    public Shoes() {
    }

    public Shoes(String shoe_name, String shoe_brand, float shoe_distance) {
        this.shoe_name = shoe_name;
        this.shoe_brand = shoe_brand;
        this.shoe_distance = shoe_distance;
    }

    public String getshoe_name() {
        return shoe_name;
    }

    public String getshoe_brand() {
        return shoe_brand;
    }

    public float getshoe_distance() {
        return shoe_distance;
    }


    public void setshoe_name(String shoe_name) {
        this.shoe_name = shoe_name;
    }

    public void setshoe_brand(String shoe_brand) {
        this.shoe_brand = shoe_brand;
    }

    public void setshoe_distance(float shoe_distance) {
        this.shoe_distance = shoe_distance;
    }
}
