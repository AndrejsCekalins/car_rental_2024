package org.rental.core.underwriting;

import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;

public interface CarRentPriceUnderwriting {

    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request);
}
