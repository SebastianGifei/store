package com.poc.store.util;

import com.poc.store.exception.DataException;
import com.poc.store.model.StoreProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Validator {

    public void validate(StoreProduct storeProduct, String correlationId) throws DataException {
        log.info("Validating Store Product's mandatory fields.");

        List<ValidationError> errorList = new ArrayList<>();
        boolean ret = storeProduct.isValid(errorList);

        if (!ret || !errorList.isEmpty()) {
            throw new DataException(correlationId + "Missing required parameter(s): " + errorList);
        }

        log.info("Product request is valid. All mandatory fields are present.");
    }
}
