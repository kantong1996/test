package com.example.kanto.projects;

/**
 * Created by kanto on 27/3/2018.
 */

public class Item {
    private String foodname;
    private String kcal;
    private Double Protien;
    private Double Carbohydate;
    private Double Fat;
    private Double total;
    private int Mid;
    private int foodid;
    private Category category;
    public Item(int foodid, String name, String kcal,Double protien,Double fat,Double carbohydate,int mid,Category category){
        this.foodid = foodid;
        this.foodname = name;
        this.kcal = kcal;
        this.Protien = protien;
        this.Fat = fat;
        this.Carbohydate = carbohydate;
        this.category = category;
        this.Mid = mid;
    }

    public int getCategoryId() {
        return category.getId();
    }
    public Integer getMID(){
        return Mid;
    }
    public Integer getFoodID(){
        return foodid;
    }
    public String getFoodName() {
        return foodname;
    }

    public void setName(String name) {
        this.foodname = name;
    }
    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Double getProtien() {
        return Protien;
    }

    public void setProtien(Double protien) {
        this.Protien = protien;
    }
    public Double getCarbohydate() {
        return Carbohydate;
    }

    public void setCarbohydate(Double carbohydate) {
        this.Carbohydate = carbohydate;
    }
    public Double getFat() {
        return Fat;
    }

    public void setFat(Double fat) {
        this.Fat = fat;
    }
}
