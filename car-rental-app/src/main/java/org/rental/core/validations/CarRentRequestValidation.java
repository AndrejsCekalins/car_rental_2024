package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.util.Optional;

public interface CarRentRequestValidation {

    Optional<ValidationError> execute(CarRentPriceCalculationRequest request);
}
