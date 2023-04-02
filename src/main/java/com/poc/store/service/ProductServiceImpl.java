package com.poc.store.service;

import com.poc.store.exception.ResourceNotFoundException;
import com.poc.store.model.database.Product;
import com.poc.store.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product, String correlationId) throws ConstraintViolationException, DataIntegrityViolationException {
        log.info(correlationId + "Saving product in DB.");
        try {
            product = productRepository.saveAndFlush(product);
        } catch (DataIntegrityViolationException exception) {
            if (exception.getCause() instanceof ConstraintViolationException) {
                throw new ConstraintViolationException(
                        correlationId + "Failed to save product in DB as product with same name already exists.",
                        ((ConstraintViolationException) exception.getCause()).getSQLException(),
                        ((ConstraintViolationException) exception.getCause()).getConstraintName());
            } else {
                log.error(correlationId + "Failed to save product in DB.");
                throw exception;
            }
        }
        log.info(correlationId + "Successfully saved product in DB: " + product);
        return product;
    }

    @Override
    public List<Product> retrieveAllProducts(String correlationId) {
        log.info(correlationId + "Retrieving all products from DB.");
        List<Product> products;
        try {
            products = productRepository.findAll();
        } catch (Exception exception) {
            log.error(correlationId + "Failed to retrieve products from DB.");
            throw exception;
        }
        log.info(correlationId + "Successfully retrieved all products from DB.");
        return products;
    }

    @Override
    public Product findProductById(int productId, String correlationId) throws ResourceNotFoundException {
        log.info(correlationId + "Retrieving product with id = " + productId + " from DB.");
        Product product;
        try {
            product = productRepository.findByProductId(productId);
            if (product == null) {
                throw new ResourceNotFoundException(correlationId +
                        "Product with id =  " + productId + " was not found in DB.");
            }
        } catch (Exception exception) {
            log.error(correlationId + "Failed to retrieve product with id = " + productId + " from DB.");
            throw exception;
        }
        log.info(correlationId + "Successfully retrieved product with id = " + productId + " from DB.");
        return product;
    }

    @Override
    public String deleteProduct(int productId, String correlationId) {
        log.info(correlationId + "Deleting product with id = " + productId + " from DB.");
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException exception) {
            log.error(correlationId + "Product with id = " + productId + " is not available in DB.");
            throw exception;
        } catch (Exception exception) {
            log.error(correlationId + "Failed to delete product with id = " + productId + " from DB.");
            throw exception;
        }
        String ret = correlationId + "Successfully deleted product with id = " + productId + " from DB.";
        log.info(ret);
        return ret;
    }
}
