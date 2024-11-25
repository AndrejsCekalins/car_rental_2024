package org.rental.core.validations;

import org.rental.core.util.ErrorCodeUnit;
import org.rental.core.util.Placeholder;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationErrorFactory {

    @Autowired
    private ErrorCodeUnit errorCodeUnit;

    public ValidationError buildError(String errorCode) {
        String errorDescription = errorCodeUnit.getErrorDescription(errorCode);
        return new ValidationError(errorCode, errorDescription);
    }

    ValidationError buildError(String errorCode, List<Placeholder> placeholders){
        String errorDescription = errorCodeUnit.getErrorDescription(errorCode, placeholders);
        return new ValidationError(errorCode, errorDescription);
    }
}
