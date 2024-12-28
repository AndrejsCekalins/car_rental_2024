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
public class SelectedCarValidation extends CarRentRequestValidationImpl {

    @Autowired private ClassifierValueRepository classifierValueRepository;
    @Autowired private ValidationErrorFactory errorFactory;

    @Override
    public List<ValidationError> validateList(CarRentPriceCalculationRequest request) {
        return request.getSelectedCar() != null
                ? validateSelectedVehicle(request)
                : List.of();
    }

    private List<ValidationError> validateSelectedVehicle(CarRentPriceCalculationRequest request){
        return request.getSelectedCar().stream()
                .map(this::validateCarIc)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    private Optional<ValidationError> validateCarIc(String carIc){
        return !existInDatabase(carIc)
                ? Optional.of(buildValidationError(carIc))
                : Optional.empty();
    }

    private ValidationError buildValidationError(String carIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_CAR_TYPE", carIc);
        return errorFactory.buildError("ERROR_CODE_9", List.of(placeholder));
    }

    private boolean existInDatabase(String carIc) {
        return classifierValueRepository.findByClassifierTitleAndIc("CAR_TYPE", carIc).isPresent();
    }
}
