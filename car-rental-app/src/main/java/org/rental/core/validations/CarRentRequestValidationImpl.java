package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.util.List;
import java.util.Optional;

abstract class CarRentRequestValidationImpl implements CarRentRequestValidation {

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        return Optional.empty();
    };

    @Override
    public List<ValidationError> validateList(CarRentPriceCalculationRequest request) {return null;};
}
