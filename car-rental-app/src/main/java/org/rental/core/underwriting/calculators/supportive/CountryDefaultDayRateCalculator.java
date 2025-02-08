package org.rental.core.underwriting.calculators.supportive;

import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;

public interface CountryDefaultDayRateCalculator {

    BigDecimal calculate(CarRentPriceCalculationRequest request);
}
