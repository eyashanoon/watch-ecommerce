package com.watches.backend.model;

import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    private String data; // encoded using base64

    public Image() {
    }

    public Image(Long id, String filename, Product product, String data) {
        this.id = id;
        this.filename = filename;
        this.product = product;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getData() {
        return data;
    }

    public void setData(String  data) {
        this.data = data;
    }
}
