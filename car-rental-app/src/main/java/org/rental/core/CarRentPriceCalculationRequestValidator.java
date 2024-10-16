package org.rental.core;

import org.rental.core.validations.CarRentRequestValidation;
import org.rental.dto.ValidationError;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class CarRentPriceCalculationRequestValidator {

    @Autowired List<CarRentRequestValidation> carRentValidations;

    public List<ValidationError> validate(CarRentPriceCalculationRequest request) {
        return carRentValidations.stream()
                .map(validation ->validation.execute(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

}
