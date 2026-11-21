
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private String phone;
    private String licenseNumber;
    private String createdAt;

    public User(int id, String firstName, String lastName,
                String username, String passwordHash,
                String phone, String licenseNumber, String createdAt) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
