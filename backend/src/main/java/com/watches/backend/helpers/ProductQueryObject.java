package com.watches.backend.helpers;


public class ProductQueryObject {
    private String productName = null;
    private String productDescription = null;
    private String brand = null;
    private Integer maxPrice = Integer.MAX_VALUE;
    private Integer minPrice = -1;
    private Integer page = 1;
    private Integer pageSize = 20;

    public ProductQueryObject() {}

    public ProductQueryObject(String productName,
                              String productDescription,
                              String brand,
                              Integer MaxPrice,
                              Integer MinPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.maxPrice = MaxPrice;
        this.minPrice = MinPrice;
    }

    public ProductQueryObject(String productName,
                              String productDescription,
                              String brand,
                              Integer MaxPrice,
                              Integer MinPrice,
                              Integer page,
                              Integer pageSize) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.brand = brand;
        this.maxPrice = MaxPrice;
        this.minPrice = MinPrice;
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

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
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
