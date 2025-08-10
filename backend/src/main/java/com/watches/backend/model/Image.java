package com.watches.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    @Lob
    @Column(name = "data", columnDefinition = "LONGTEXT")
    private byte[] data;

    private boolean deleted = false;
}