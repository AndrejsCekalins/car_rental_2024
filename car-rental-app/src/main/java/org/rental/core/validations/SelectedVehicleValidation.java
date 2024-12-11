package org.rental.core.validations;

import org.rental.core.repositories.ClassifierValueRepository;
import org.rental.core.util.Placeholder;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SelectedVehicleValidation extends CarRentRequestValidationImpl {

    @Autowired private ClassifierValueRepository classifierValueRepository;
    @Autowired private ValidationErrorFactory errorFactory;

    @Override
    public List<ValidationError> validateList(CarRentPriceCalculationRequest request) {
        return request.getSelectedVehicle() != null
                ? validateSelectedVehicle(request)
                : List.of();
    }

    private List<ValidationError> validateSelectedVehicle(CarRentPriceCalculationRequest request){
        return request.getSelectedVehicle().stream()
                .map(this::validateVehicleIc)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    private Optional<ValidationError> validateVehicleIc(String vehicleIc){
        return !existInDatabase(vehicleIc)
                ? Optional.of(buildValidationError(vehicleIc))
                : Optional.empty();
    }

    private ValidationError buildValidationError(String vehicleIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_VEHICLE_TYPE", vehicleIc);
        return errorFactory.buildError("ERROR_CODE_9", List.of(placeholder));
    }

    private boolean existInDatabase(String vehicleIc) {
        return classifierValueRepository.findByClassifierTitleAndIc("VEHICLE_TYPE", vehicleIc).isPresent();
    }
}
