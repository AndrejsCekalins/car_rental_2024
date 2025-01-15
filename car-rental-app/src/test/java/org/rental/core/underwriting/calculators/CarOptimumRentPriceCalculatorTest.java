package org.rental.core.underwriting.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.AgeCoefficient;
import org.rental.core.domain.CountryDefaultDayRate;
import org.rental.core.repositories.AgeCoefficientRepository;
import org.rental.core.repositories.CountryDefaultDayRateRepository;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarOptimumRentPriceCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;
    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;
    @Mock
    private AgeCoefficientRepository ageCoefficientRepository;

    @InjectMocks
    private CarOptimumRentPriceCalculator calculator;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp(){
        request =mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonBirthDate()).thenReturn(new Date());
    }

    @Test
    void shouldCalculatePremiumCorrectly() {
        BigDecimal daysCount =BigDecimal.valueOf(10);
        BigDecimal countryDefaultRate = BigDecimal.valueOf(20);
        BigDecimal ageCoefficient = BigDecimal.valueOf(1.2);

        LocalDate currentDate = LocalDate.now();

        when(dateTimeUtil.getCurrentDateTime()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(dateTimeUtil.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo())).thenReturn(daysCount.longValue());
        CountryDefaultDayRate countryDefaultDayRate = mock(CountryDefaultDayRate.class);
        when(countryDefaultDayRate.getDefaultDayRate()).thenReturn(countryDefaultRate);
        when(countryDefaultDayRateRepository.findByCountryIc(request.getCountry())).thenReturn(Optional.of(countryDefaultDayRate));
        AgeCoefficient ageCoefficientDomain = mock(AgeCoefficient.class);
        when(ageCoefficientDomain.getCoefficient()).thenReturn(ageCoefficient);
        when(ageCoefficientRepository.findCoefficient(calculatedAge(request))).thenReturn(Optional.of(ageCoefficientDomain));

        BigDecimal expectedPrice = countryDefaultRate.multiply(daysCount).multiply(ageCoefficient)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal result = calculator.calculatePrice(request);

        assertEquals(expectedPrice, result);
    }

    private Integer calculatedAge(CarRentPriceCalculationRequest request){
        LocalDate personBirthDate = toLocalDate(request.getPersonBirthDate());
        LocalDate currentDate = toLocalDate(dateTimeUtil.getCurrentDateTime());
        return Period.between(personBirthDate, currentDate).getYears();
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}