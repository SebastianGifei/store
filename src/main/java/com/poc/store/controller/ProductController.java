package com.poc.store.controller;

import com.poc.store.exception.DataException;
import com.poc.store.exception.ResourceNotFoundException;
import com.poc.store.model.StoreProduct;
import com.poc.store.model.database.Product;
import com.poc.store.service.ProductService;
import com.poc.store.util.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@EnableWebSecurity
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Converter converter;

    @Autowired
    private Validator validator;

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProducts() {
        String correlationId = Helper.getNewCorrelationId() + ": ";
        try{
            // Receive request
            log.info(correlationId + "Received Retrieve all products request.");

            // Return all products from DB
            return new ResponseEntity<>(productService.retrieveAllProducts(correlationId), HttpStatus.OK);

        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.UNKNOWN_ERROR, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        String correlationId = Helper.getNewCorrelationId() + ": ";
        try{
            // Receive request
            log.info(correlationId + "Received Find product by id request.");

            // Return product from DB
            return new ResponseEntity<>(productService.findProductById(productId, correlationId), HttpStatus.OK);

        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.RESOURCE_NOT_FOUND_ERROR, exception.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.UNKNOWN_ERROR, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody String product) {
        StoreProduct storeProduct = new StoreProduct();
        String correlationId = Helper.getNewCorrelationId() + ": ";
        try{
            // Receive request
            log.info(correlationId + "Received Create Product request.");

            // Converting payload
            storeProduct = converter.convertRequestBody(product, correlationId);

            // Validate payload
            validator.validate(storeProduct, correlationId);

            // Convert to DB model
            Product dbProduct = converter.convertToDbProduct(storeProduct, correlationId);

            // Saving the new product
            return new ResponseEntity<>(productService.saveProduct(dbProduct, correlationId), HttpStatus.CREATED);

        } catch (DataException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.DATA_ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.RESOURCE_NOT_FOUND_ERROR, exception.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(exception.getConstraintName(), exception.getMessage()), HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.DB_ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.UNKNOWN_ERROR, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody String product, @PathVariable Integer productId) {
        StoreProduct storeProduct = new StoreProduct();
        String correlationId = Helper.getNewCorrelationId() + ": ";
        try{
            // Receive request
            log.info(correlationId + "Received Update Product request.");

            // Retrieve existing product
            Product existingProduct = productService.findProductById(productId, correlationId);

            // Converting payload
            storeProduct = converter.convertRequestBody(product, correlationId);

            // Convert to DB model for update by patching
            Product dbProduct = converter.convertToDbProductForUpdate(existingProduct, storeProduct, correlationId);

            // Updating the product
            return new ResponseEntity<>(productService.saveProduct(dbProduct, correlationId), HttpStatus.OK);

        } catch (DataException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.DATA_ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.RESOURCE_NOT_FOUND_ERROR, exception.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(exception.getConstraintName(), exception.getMessage()), HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.DB_ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.UNKNOWN_ERROR, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        String correlationId = Helper.getNewCorrelationId() + ": ";
        try{
            // Receive request
            log.info(correlationId + "Received Delete product by id request.");

            // Delete product from DB
            String ret = productService.deleteProduct(productId, correlationId);
            return new ResponseEntity<>(new SuccessResponse(ret), HttpStatus.OK);

        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.RESOURCE_NOT_FOUND_ERROR, exception.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(
                    new ErrorResponse(Constants.UNKNOWN_ERROR, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
