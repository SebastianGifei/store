package com.poc.store.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.store.exception.DataException;
import com.poc.store.exception.ResourceNotFoundException;
import com.poc.store.model.StoreProduct;
import com.poc.store.model.database.Manufacturer;
import com.poc.store.model.database.Product;
import com.poc.store.model.database.ProductType;
import com.poc.store.repository.ManufacturerRepository;
import com.poc.store.repository.ProductTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Converter {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public StoreProduct convertRequestBody(String payload, String correlationId) throws DataException {
        log.info(correlationId + "Converting request body to StoreProduct type.");
        ObjectMapper mapper = new ObjectMapper();
        try {
            StoreProduct storeProduct = mapper.readValue(payload, StoreProduct.class);
            log.info(correlationId + "Product successfully processed: " + storeProduct);
            return storeProduct;
        } catch (JsonProcessingException exception) {
            throw new DataException(correlationId + "Request payload has an invalid format.");
        }
    }

    public Product convertToDbProduct(StoreProduct storeProduct, String correlationId) throws ResourceNotFoundException{
        log.info(correlationId + "Converting input product to db product.");

        ProductType productType = productTypeRepository.findByProductName(storeProduct.getProductType().toUpperCase());
        if (productType == null) {
            throw new ResourceNotFoundException(correlationId +
                    "Dependency resource ProductType " + storeProduct.getProductType() + " was not found in DB.");
        }

        Manufacturer manufacturer = manufacturerRepository.findByManufacturerName(storeProduct.getProductManufacturer().toUpperCase());
        if (manufacturer == null) {
            throw new ResourceNotFoundException(correlationId +
                    "Dependency resource Manufacturer " + storeProduct.getProductType() + " was not found in DB.");
        }

        log.info("Input Product successfully converted into DB Product.");
        return Product.builder()
                .productName(storeProduct.getProductName())
                .productDescription(storeProduct.getProductDescription())
                .productPrice(storeProduct.getProductPrice())
                .productType(productType)
                .productManufacturer(manufacturer)
                .build();
    }

    public Product convertToDbProductForUpdate(Product existingProduct,
                                               StoreProduct storeProduct,
                                               String correlationId) throws ResourceNotFoundException{
        log.info(correlationId + "Converting input product to db product.");

        ProductType productType = null;
        Manufacturer manufacturer = null;

        if (storeProduct.getProductType() != null) {
            productType = productTypeRepository.findByProductName(storeProduct.getProductType().toUpperCase());
            if (productType == null) {
                throw new ResourceNotFoundException(correlationId +
                        "Dependency resource ProductType " + storeProduct.getProductType() + " was not found in DB.");
            }
        }

        if (storeProduct.getProductManufacturer() != null) {
            manufacturer = manufacturerRepository.findByManufacturerName(storeProduct.getProductManufacturer().toUpperCase());
            if (manufacturer == null) {
                throw new ResourceNotFoundException(correlationId +
                        "Dependency resource Manufacturer " + storeProduct.getProductType() + " was not found in DB.");
            }
        }

        log.info("Input Product successfully converted into DB Product.");
        return Product.builder()
                .productId(existingProduct.getProductId())
                .productName(storeProduct.getProductName() == null ? existingProduct.getProductName() : storeProduct.getProductName())
                .productDescription(storeProduct.getProductDescription() == null ? existingProduct.getProductDescription() : storeProduct.getProductDescription())
                .productPrice(storeProduct.getProductPrice() == null ? existingProduct.getProductPrice() : storeProduct.getProductPrice())
                .productType(storeProduct.getProductType() == null ? existingProduct.getProductType() : productType)
                .productManufacturer(storeProduct.getProductManufacturer() == null ? existingProduct.getProductManufacturer() : manufacturer)
                .build();
    }
}
