package com.poc.store.model;

import com.poc.store.util.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreProductTest {

    private StoreProduct storeProduct;

    List<ValidationError> errorList;

    @BeforeEach
    void setUp() {
        errorList = new ArrayList<>();
        storeProduct = StoreProduct.builder()
                .productManufacturer("APPLE")
                .productDescription("Older iphone")
                .productType("PHONE")
                .productPrice("800")
                .productName("IPHONE-12")
                .build();
    }

    @Test
    void isValidAllFields() {
        boolean ret = storeProduct.isValid(errorList);
        assertTrue(ret, "StoreProduct object should be valid.");
    }

    @Test
    void isValidAllFieldsWithoutDescription() {
        storeProduct.setProductDescription(null);
        boolean ret = storeProduct.isValid(errorList);
        assertTrue(ret, "StoreProduct object should be valid as description is optional.");
    }

    @Test
    void isNotValidEmptyManufacturer() {
        storeProduct.setProductManufacturer("");
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as manufacturer should not be empty.");
    }

    @Test
    void isNotValidNullManufacturer() {
        storeProduct.setProductManufacturer(null);
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as manufacturer should not be null.");
    }

    @Test
    void isNotValidEmptyProductType() {
        storeProduct.setProductType("");
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as ProductType should not be empty.");
    }

    @Test
    void isNotValidNullProductType() {
        storeProduct.setProductType(null);
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as ProductType should not be null.");
    }

    @Test
    void isNotValidEmptyProductPrice() {
        storeProduct.setProductPrice("");
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as ProductType should not be empty.");
    }

    @Test
    void isNotValidNullProductPrice() {
        storeProduct.setProductPrice(null);
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as ProductPrice should not be null.");
    }

    @Test
    void isNotValidEmptyProductName() {
        storeProduct.setProductName("");
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as ProductName should not be empty.");
    }

    @Test
    void isNotValidNullProductName() {
        storeProduct.setProductName(null);
        boolean ret = storeProduct.isValid(errorList);
        assertFalse(ret, "StoreProduct object should not be valid as ProductName should not be null.");
    }
}