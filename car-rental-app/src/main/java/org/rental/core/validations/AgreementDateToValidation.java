package org.rental.core.validations;

import org.rental.core.ErrorCodeUnit;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class AgreementDateToValidation implements CarRentRequestValidation {

    @Autowired
    private ErrorCodeUnit errorCodeUnit;

    @Override
    public Optional<ValidationError> execute(CarRentPriceCalculationRequest request) {
        return (request.getAgreementDateTo() == null)
                ? Optional.of(buildError("ERROR_CODE_4"))
                : Optional.empty();
    }

    private ValidationError buildError(String errorCode){
        String errorDescription = errorCodeUnit.getErrorDescription(errorCode);
        return new ValidationError(errorCode, errorDescription);
    }
}
