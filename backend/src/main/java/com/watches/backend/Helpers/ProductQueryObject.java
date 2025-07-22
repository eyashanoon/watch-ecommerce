package com.watches.backend.Helpers;

import com.watches.backend.enums.Gender;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductQueryObject {
    private String productName = null;
    private String productDescription = null;
    private String brand = null;
    private Gender gender = null;
    private Integer Maxprice = Integer.MAX_VALUE;
    private Integer Minprice = -1;
    private Integer page = 1;
    private Integer pageSize = 20;

    public ProductQueryObject() {}

    public ProductQueryObject(String productName,
                              String productDescription,
                              String brand,
                              Gender gender,
                              Integer MaxPrice,
                              Integer MinPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.gender = gender;
        this.Maxprice = MaxPrice;
        this.Minprice = MinPrice;
    }

    public ProductQueryObject(String productName,
                              String productDescription,
                              String brand,
                              Gender gender,
                              Integer MaxPrice,
                              Integer MinPrice,
                              Integer page,
                              Integer pageSize) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.gender = gender;
        this.Maxprice = MaxPrice;
        this.Minprice = MinPrice;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getMaxprice() {
        return Maxprice;
    }

    public void setMaxprice(Integer maxprice) {
        Maxprice = maxprice;
    }

    public Integer getMinprice() {
        return Minprice;
    }

    public void setMinprice(Integer minprice) {
        Minprice = minprice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
