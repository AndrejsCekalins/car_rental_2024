package org.rental.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmptyCarLuxInsuranceCoverValidation extends CarRentRequestValidationImpl {

    @Value( "${car.lux.insurance.cover.type.enabled:false}" )
    private Boolean carLuxInsuranceCoverTypeEnabled;

    @Autowired
    private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        return (isCarLuxInsuranceCoverTypeEnabled()
                && containsCarLux(request)
                && isCarLuxInsuranceCoverTypeIsNullOrBlank(request))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_13"))
                : Optional.empty();
    }

    private boolean isCarLuxInsuranceCoverTypeEnabled() {return carLuxInsuranceCoverTypeEnabled;}

    private boolean containsCarLux (CarRentPriceCalculationRequest request) {
        return request.getSelectedCar() != null
        && request.getSelectedCar().contains("CAR_LUX");
    }

    private boolean isCarLuxInsuranceCoverTypeIsNullOrBlank(CarRentPriceCalculationRequest request){
        return request.getCarLuxInsuranceCoverType() == null|| request.getCarLuxInsuranceCoverType().isEmpty();
    }
}
