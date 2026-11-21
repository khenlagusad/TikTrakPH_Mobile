
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

public class Enforcer {

    private int id;
    private String firstName;
    private String lastName;
    private Integer cityId;

    public Enforcer(int id, String firstName, String lastName,
                    Integer cityId) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getFullName() {
        if (firstName == null && lastName == null) {
            return "Unknown Enforcer";
        }

        if (firstName == null) {
            return lastName;
        }

        if (lastName == null) {
            return firstName;
        }

        return firstName + " " + lastName;
    }
}
