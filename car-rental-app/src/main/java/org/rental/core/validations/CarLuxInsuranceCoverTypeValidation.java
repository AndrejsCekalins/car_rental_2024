package org.rental.core.validations;

import lombok.AllArgsConstructor;
import org.rental.core.repositories.ClassifierValueRepository;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
class CarLuxInsuranceCoverTypeValidation extends CarRentRequestValidationImpl {

    @Value( "${car.lux.insurance.cover.type.enabled:false}" )
    private Boolean carLuxInsuranceCoverTypeEnabled;

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory errorFactory;

    CarLuxInsuranceCoverTypeValidation(ClassifierValueRepository classifierValueRepository, ValidationErrorFactory errorFactory) {
        this.classifierValueRepository = classifierValueRepository;
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationError>validate(CarRentPriceCalculationRequest request) {
        return (isCarLuxInsuranceCoverTypeEnable()
                && containsCarLuxInsuranceCoverType(request)
                && isCarLuxInsuranceCoverTypeNotBlank(request))
                && !existInDatabase(request.getCarLuxInsuranceCoverType())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean isCarLuxInsuranceCoverTypeEnable() {
        return carLuxInsuranceCoverTypeEnabled;
    }

    private boolean containsCarLuxInsuranceCoverType(CarRentPriceCalculationRequest request) {
        return request.getSelectedCar() !=null
                && request.getSelectedCar().contains("CAR_LUX");
    }

    private boolean isCarLuxInsuranceCoverTypeNotBlank(CarRentPriceCalculationRequest request) {
        return request.getCarLuxInsuranceCoverType()!= null
                && !request.getCarLuxInsuranceCoverType().isBlank();
    }

    private boolean existInDatabase(String carLuxInsuranceCoverTypeIc) {
        return classifierValueRepository.
                findByClassifierTitleAndIc("CAR_LUX_INSURANCE_COVER_TYPE",
                        carLuxInsuranceCoverTypeIc).isPresent();

    }
}
