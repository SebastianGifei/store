package com.poc.store.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.store.util.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreProduct {

    @NotEmpty(message = "productName should not be null or empty.")
    private String productName;

    private String productDescription;

    @NotEmpty(message = "productPrice should not be null or empty.")
    private String productPrice;

    @NotEmpty(message = "productType should not be null or empty.")
    private String productType;

    @NotEmpty(message = "productManufacturer should not be null or empty.")
    private String productManufacturer;

    public boolean isValid(List<ValidationError> errorList) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        boolean ret = true;

        Set<ConstraintViolation<StoreProduct>> errorResp = validator.validate(this);

        if (!errorResp.isEmpty()) {
            for (ConstraintViolation<StoreProduct> error : errorResp) {
                errorList.add(new ValidationError(error.getPropertyPath().toString(), error.getMessage()));
            }
            ret = false;
        }

        return ret;
    }
}
