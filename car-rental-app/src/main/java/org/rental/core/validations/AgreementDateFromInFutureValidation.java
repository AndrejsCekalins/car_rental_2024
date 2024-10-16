package org.rental.core.validations;

import org.rental.core.DateTimeService;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class AgreementDateFromInFutureValidation implements CarRentRequestValidation {

    @Autowired
    private DateTimeService dateTimeService;

    @Override
    public Optional<ValidationError> execute(CarRentPriceCalculationRequest request) {
        Date dateFrom = request.getAgreementDateFrom();
        Date currentDateTime =dateTimeService.getCurrentDateTime();
        return (dateFrom != null && dateFrom.before(currentDateTime))
                ? Optional.of(new ValidationError("agreementDateFrom", "Must be in the future!"))
                : Optional.empty();
    }
}
