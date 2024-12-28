package org.rental.core.underwriting;

import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;

interface CarRentPriceCalculator {

    BigDecimal calculatePrice(CarRentPriceCalculationRequest request);

    String getCarIc();
}
