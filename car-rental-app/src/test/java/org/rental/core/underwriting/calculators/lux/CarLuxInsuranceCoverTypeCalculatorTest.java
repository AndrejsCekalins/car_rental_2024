package org.rental.core.underwriting.calculators.lux;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.CarLuxInsuranceCoverType;
import org.rental.core.repositories.CarLuxInsuranceCoverTypeRepository;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarLuxInsuranceCoverTypeCalculatorTest {

    @Mock
    private CarLuxInsuranceCoverTypeRepository carLuxInsuranceCoverTypeRepository;

    @InjectMocks
    private CarLuxInsuranceCoverTypeCalculator calculator;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp() {
        request = new CarRentPriceCalculationRequest();
        request.setCarLuxInsuranceCoverType("PREMIUM_INSURANCE");
    }

    @Test
    void shouldFindCarLuxInsuranceCoverTypeWhenInsuranceCoverTypeExists() {

        ReflectionTestUtils.setField(calculator, "carLuxInsuranceCoverTypeEnabled", true);
        BigDecimal expectedCarLuxInsuranceCoverType = BigDecimal.valueOf(1.20);

        CarLuxInsuranceCoverType carLuxInsuranceCoverType = mock(CarLuxInsuranceCoverType.class);
        when(carLuxInsuranceCoverType.getCoefficient()).thenReturn(expectedCarLuxInsuranceCoverType);

        when(carLuxInsuranceCoverTypeRepository.findByCarLuxInsuranceCoverTypeIc(request.getCarLuxInsuranceCoverType()))
                .thenReturn(Optional.of(carLuxInsuranceCoverType));

        BigDecimal result = calculator.calculate(request);

        Assertions.assertEquals(expectedCarLuxInsuranceCoverType, result);
    }


    @Test
    void shouldThrowExceptionWhenCarLuxInsuranceCoverTypeNotFound() {
        ReflectionTestUtils.setField(calculator, "carLuxInsuranceCoverTypeEnabled", true);
        when(carLuxInsuranceCoverTypeRepository.findByCarLuxInsuranceCoverTypeIc(request.getCarLuxInsuranceCoverType()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> calculator.calculate(request));

        assertEquals("Car lux insurance cover type not found by = " +request.getCarLuxInsuranceCoverType(),
                exception.getMessage());
    }

}