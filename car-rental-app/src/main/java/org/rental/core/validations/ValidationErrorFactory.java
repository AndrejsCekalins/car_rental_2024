package org.rental.core.validations;

import org.rental.core.ErrorCodeUnit;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationErrorFactory {

    @Autowired
    private ErrorCodeUnit errorCodeUnit;

    public ValidationError buildError(String errorCode) {
        String errorDescription = errorCodeUnit.getErrorDescription(errorCode);
        return new ValidationError(errorCode, errorDescription);
    }
}
