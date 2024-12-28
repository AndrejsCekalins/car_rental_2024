package org.rental.core.underwriting;

import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;

public interface CarRentPriceUnderwriting {

    BigDecimal calculatePrice(CarRentPriceCalculationRequest request);
}
