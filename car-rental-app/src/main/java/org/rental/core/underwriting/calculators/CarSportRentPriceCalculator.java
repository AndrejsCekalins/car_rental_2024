package org.rental.core.underwriting.calculators;

import org.rental.core.underwriting.CarRentPriceCalculator;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CarSportRentPriceCalculator implements CarRentPriceCalculator {

    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request) {return BigDecimal.ZERO;}

    @Override
    public String getCarIc() {return "CAR_SPORT";}
}
