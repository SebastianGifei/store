package com.poc.store.repository;

import com.poc.store.model.database.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    ProductType findByProductName(@Param("product_name") String productName);
}
