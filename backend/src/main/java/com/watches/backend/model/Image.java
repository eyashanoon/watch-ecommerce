package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @ManyToOne  (cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Lob
    @Column(name = "data", columnDefinition = "LONGTEXT")
    private String data; // encoded using base64

    public Image() {
    }

    public Image(Long id, String filename, Product product, String data) {
        this.id = id;
        this.filename = filename;
        this.product = product;
        this.data = data;
    }
}
