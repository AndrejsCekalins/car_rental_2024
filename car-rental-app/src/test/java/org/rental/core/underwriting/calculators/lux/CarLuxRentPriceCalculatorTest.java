package org.rental.core.underwriting.calculators.lux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.underwriting.calculators.supportive.AgeCoefficientCalculator;
import org.rental.core.underwriting.calculators.supportive.CountryDefaultDayRateCalculator;
import org.rental.core.underwriting.calculators.supportive.DayCountCalculator;
import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarLuxRentPriceCalculatorTest {

    @Mock private DayCountCalculator dayCountCalculator;
    @Mock private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    @Mock private AgeCoefficientCalculator ageCoefficientCalculator;
    @Mock private CarLuxInsuranceCoverTypeCalculator carLuxInsuranceCoverTypeCalculator;

    @InjectMocks
    private CarLuxRentPriceCalculator calculator;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp() {
        request = new CarRentPriceCalculationRequest();
    }


    @Test
    void shouldCalculateCarLuxCorrectly() {
        BigDecimal daysCount =BigDecimal.valueOf(10);
        BigDecimal countryDefaultRate = BigDecimal.valueOf(20);
        BigDecimal ageCoefficient = BigDecimal.valueOf(1.2);
        BigDecimal insuranceType = BigDecimal.valueOf(1.0);

        when(dayCountCalculator.calculate(request)).thenReturn(daysCount);
        when(countryDefaultDayRateCalculator.calculate(request)).thenReturn(countryDefaultRate);
        when(ageCoefficientCalculator.calculate(request)).thenReturn(ageCoefficient);
        when(carLuxInsuranceCoverTypeCalculator.calculate(request)).thenReturn(insuranceType);

        BigDecimal expectedPrice = countryDefaultRate.multiply(daysCount).multiply(ageCoefficient)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal result = calculator.calculatePrice(request);

        assertEquals(expectedPrice, result);
    }

}