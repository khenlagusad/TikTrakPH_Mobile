
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

public class Vehicle {

    private int id;
    private int userId;
    private String plateNumber;
    private String brand;
    private int year;
    private String model;
    private String color;

    public Vehicle(int id, int userId, String plateNumber,
                   String brand, int year, String model, String color) {

        this.id = id;
        this.userId = userId;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.year = year;
        this.model = model;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }
}
