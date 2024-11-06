package org.rental.core.validations;

import org.rental.core.ErrorCodeUnit;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
@Component
class DateFromLessThenDateToValidation implements CarRentRequestValidation {

    @Autowired
    private ErrorCodeUnit errorCodeUnit;

    @Override
    public Optional<ValidationError> execute(CarRentPriceCalculationRequest request) {
        Date dateFrom = request.getAgreementDateFrom();
        Date dateTo =request.getAgreementDateTo();
        return (dateFrom != null && dateTo != null &&
                (dateFrom.equals(dateTo) || dateFrom.after(dateTo)))
                ? Optional.of(buildError("ERROR_CODE_5"))
                : Optional.empty();
    }

    private ValidationError buildError(String errorCode) {
        String errorDescription = errorCodeUnit.getErrorDescription(errorCode);
        return new ValidationError(errorCode, errorDescription);
    }
}
