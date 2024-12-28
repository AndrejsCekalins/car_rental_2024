package org.rental.core.underwriting;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CarLuxRentPriceCalculator implements  CarRentPriceCalculator {
    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request) {return BigDecimal.ZERO;}

    @Override
    public String getCarIc() {return "CAR_LUX";}
}
