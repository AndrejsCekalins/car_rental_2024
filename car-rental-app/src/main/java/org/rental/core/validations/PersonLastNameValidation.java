package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PersonLastNameValidation extends CarRentRequestValidationImpl {

    @Autowired
    private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        return (request.getPersonLastName() == null || request.getPersonLastName().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_8"))
                : Optional.empty();
    }
}
