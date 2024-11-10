package model.entities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hello
 */
public class Address {
    private String provinceOrCity;
    private String district;
    private String communeOrWardOrTown;
    private String street;
    private String houseNumber;

    public String getProvinceOrCity() {
        return provinceOrCity;
    }

    public void setProvinceOrCity(String provinceOrCity) {
        this.provinceOrCity = provinceOrCity;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommuneOrWardOrTown() {
        return communeOrWardOrTown;
    }

    public void setCommuneOrWardOrTown(String communeOrWardOrTown) {
        this.communeOrWardOrTown = communeOrWardOrTown;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}

