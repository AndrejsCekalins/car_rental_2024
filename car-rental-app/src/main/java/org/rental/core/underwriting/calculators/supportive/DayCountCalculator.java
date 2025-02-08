package org.rental.core.underwriting.calculators.supportive;

import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;

public interface DayCountCalculator {

    BigDecimal calculate(CarRentPriceCalculationRequest request);
}
