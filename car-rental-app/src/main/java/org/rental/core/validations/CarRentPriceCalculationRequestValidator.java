package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.util.List;

public interface CarRentPriceCalculationRequestValidator {

    List<ValidationError> validate(CarRentPriceCalculationRequest request);
}
