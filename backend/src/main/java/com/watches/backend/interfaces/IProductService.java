package com.watches.backend.interfaces;

import com.watches.backend.Dto.ProductDto.CreateProductDto;
import com.watches.backend.helpers.ProductQueryObject;
import com.watches.backend.model.Product;

import java.net.URI;
import java.util.List;

public interface IProductService {
    public URI create(Product product);
    public Product update(CreateProductDto createProductDto, Long id);
    public Product delete(Long id);
    public Product findById(Long id);
    public List<Product> findAll(ProductQueryObject queryObject);
}
