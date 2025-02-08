package org.rental.core.underwriting.calculators.supportive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.AgeCoefficient;
import org.rental.core.repositories.AgeCoefficientRepository;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AgeCoefficientCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;
    @Mock
    private AgeCoefficientRepository ageCoefficientRepository;

    @InjectMocks
    private AgeCoefficientCalculatorImpl calculator;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp() {
        request = new CarRentPriceCalculationRequest();
        request.setPersonBirthDate(Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }


    @Test
    void shouldFindCoefficientWhenAgeCoefficientExists() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", true);
        BigDecimal expectedCoefficient = BigDecimal.valueOf(1.2);
        LocalDate currentDate = LocalDate.now();

        when(dateTimeUtil.getCurrentDateTime()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        AgeCoefficient ageCoefficient = mock(AgeCoefficient.class);
        when(ageCoefficient.getCoefficient()).thenReturn(expectedCoefficient);
        when(ageCoefficientRepository.findCoefficient(calculatedAge(request))).
                thenReturn(Optional.of(ageCoefficient));

        BigDecimal result = calculator.calculate(request);

        Assertions.assertEquals(expectedCoefficient, result);
    }

    @Test
    void shouldThrowExceptionWhenAgeCoefficientNotFound() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", true);
        LocalDate currentDate = LocalDate.now();

        when(dateTimeUtil.getCurrentDateTime()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(ageCoefficientRepository.findCoefficient(calculatedAge(request))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> calculator.calculate(request));

        assertEquals("Age coefficient not found for age = " + calculatedAge(request), exception.getMessage());
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