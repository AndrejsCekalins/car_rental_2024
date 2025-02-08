package org.rental.core.underwriting.calculators.lux;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.rental.core.domain.CarLuxInsuranceCoverType;
import org.rental.core.repositories.CarLuxInsuranceCoverTypeRepository;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CarLuxInsuranceCoverTypeCalculator {

    @Value( "${car.lux.insurance.cover.type:false}" )
    private Boolean carLuxInsuranceCoverTypeEnabled;

    private final CarLuxInsuranceCoverTypeRepository carLuxInsuranceCoverTypeRepository;


    BigDecimal calculate (CarRentPriceCalculationRequest request) {
    return carLuxInsuranceCoverTypeEnabled
            ? getCoefficient(request)
            : getDefaultValue();
}


private BigDecimal getCoefficient(CarRentPriceCalculationRequest request) {
    return carLuxInsuranceCoverTypeRepository.findByCarLuxInsuranceCoverTypeIc(request.getCarLuxInsuranceCoverType())
        .map(CarLuxInsuranceCoverType::getCoefficient)
        .orElseThrow(() -> new RuntimeException("Car lux insurance cover type not found by = " + request.getCarLuxInsuranceCoverType()));
}

private static BigDecimal getDefaultValue() {return BigDecimal.ONE;}

}
