package org.rental.core.validations;

import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class PersonBirthDateInFutureValidation extends CarRentRequestValidationImpl {

    @Autowired
    private DateTimeUtil dateTimeService;

    @Autowired
    private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        Date personBirthDate = request.getPersonBirthDate();
        Date currentDateTime = dateTimeService.getCurrentDateTime();
        return ( personBirthDate!= null && personBirthDate.after(currentDateTime))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_12"))
                : Optional.empty();
    }
}
