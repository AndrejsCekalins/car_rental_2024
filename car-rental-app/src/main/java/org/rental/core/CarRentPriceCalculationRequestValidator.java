package org.rental.core;

import org.rental.dto.ValidationError;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class CarRentPriceCalculationRequestValidator {

    public List<ValidationError> validate(CarRentPriceCalculationRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        validatePersonFirstName(request).ifPresent(errors::add);
        validatePersonLastName(request).ifPresent(errors::add);

        return errors;
    }

    private Optional<ValidationError> validatePersonFirstName(CarRentPriceCalculationRequest request) {
        return (request.getPersonFirstName() == null || request.getPersonFirstName().isEmpty())
                ? Optional.of(new ValidationError("personFirstName", "Must not be empty!"))
                : Optional.empty();
    }

    private Optional<ValidationError> validatePersonLastName(CarRentPriceCalculationRequest request) {
        return (request.getPersonLastName() == null || request.getPersonLastName().isEmpty())
                ? Optional.of(new ValidationError("personLastName", "Must not be empty!"))
                : Optional.empty();
    }
}
