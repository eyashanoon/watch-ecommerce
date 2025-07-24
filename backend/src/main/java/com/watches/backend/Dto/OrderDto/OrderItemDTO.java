package com.watches.backend.Dto.OrderDto;

public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Double priceAtPurchase;
    private Integer quantity;

    public OrderItemDTO() {}

    public OrderItemDTO(Long productId, String productName, Double priceAtPurchase, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.priceAtPurchase = priceAtPurchase;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(Double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
