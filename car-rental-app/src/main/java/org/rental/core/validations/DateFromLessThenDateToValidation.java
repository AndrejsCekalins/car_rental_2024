package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
@Component
class DateFromLessThenDateToValidation extends CarRentRequestValidationImpl {

    @Autowired
    private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        Date dateFrom = request.getAgreementDateFrom();
        Date dateTo =request.getAgreementDateTo();
        return (dateFrom != null && dateTo != null &&
                (dateFrom.equals(dateTo) || dateFrom.after(dateTo)))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_5"))
                : Optional.empty();
    }
}
