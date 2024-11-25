package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmptySelectedVehicleValidation extends CarRentRequestValidationImpl {

    @Autowired
    private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        return (request.getSelectedVehicle()==null || request.getSelectedVehicle().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_6"))
                : Optional.empty();
    }
}
