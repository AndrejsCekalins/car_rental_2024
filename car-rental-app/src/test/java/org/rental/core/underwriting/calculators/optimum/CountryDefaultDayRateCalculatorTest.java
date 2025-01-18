package org.rental.core.underwriting.calculators.optimum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.CountryDefaultDayRate;
import org.rental.core.repositories.CountryDefaultDayRateRepository;
import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryDefaultDayRateCalculatorTest {

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @InjectMocks
    private CountryDefaultDayRateCalculator calculator;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp() {
        request = new CarRentPriceCalculationRequest();
        request.setCountry("SPAIN");
    }

    @Test
    void shouldFindCountryDefaultDayRateWhenDefaultDayRateExists() {
        BigDecimal expectedCountryDefaultRate = BigDecimal.valueOf(20);

        CountryDefaultDayRate countryDefaultDayRate = mock(CountryDefaultDayRate.class);
        when(countryDefaultDayRate.getDefaultDayRate()).thenReturn(expectedCountryDefaultRate);

        when(countryDefaultDayRateRepository.findByCountryIc(request.getCountry())).
                thenReturn(Optional.of(countryDefaultDayRate));

        BigDecimal result = calculator.calculate(request);

        Assertions.assertEquals(expectedCountryDefaultRate, result);
    }

    @Test
    void shouldThrowExceptionWhenCountryDefaultDayRateNotFound() {

        when(countryDefaultDayRateRepository.findByCountryIc(request.getCountry())).
                thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> calculator.calculate(request));

        assertEquals("Country day rate not found by country id = " +request.getCountry(),
                exception.getMessage());
    }
}