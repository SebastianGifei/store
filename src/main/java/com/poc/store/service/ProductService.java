package com.poc.store.service;

import com.poc.store.model.database.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product, String correlationId);

    List<Product> retrieveAllProducts(String correlationId);

    Product findProductById(int productId, String correlationId);

    String deleteProduct(int productId, String correlationId);
}
