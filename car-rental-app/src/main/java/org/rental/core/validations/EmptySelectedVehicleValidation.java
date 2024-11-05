package org.rental.core.validations;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmptySelectedVehicleValidation implements CarRentRequestValidation {

    @Override
    public Optional<ValidationError> execute(CarRentPriceCalculationRequest request) {
        return (request.getSelectedVehicle()==null || request.getSelectedVehicle().isEmpty())
                ? Optional.of(new ValidationError("selectedVehicle", "Must not be empty!"))
                : Optional.empty();
    }
}
