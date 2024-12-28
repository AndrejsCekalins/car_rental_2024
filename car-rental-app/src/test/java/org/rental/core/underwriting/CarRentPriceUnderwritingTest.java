package org.rental.core.underwriting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarRentPriceUnderwritingTest {

    @InjectMocks
    private CarRentPriceUnderwritingImpl priceUnderwriting;

    private CarRentPriceCalculator carRentPriceCalculator1;
    private CarRentPriceCalculator carRentPriceCalculator2;

    @BeforeEach
    public void init(){
        carRentPriceCalculator1=mock(CarRentPriceCalculator.class);
        carRentPriceCalculator2=mock(CarRentPriceCalculator.class);
        var carRentPriceCalculators = List.of(carRentPriceCalculator1, carRentPriceCalculator2);
        ReflectionTestUtils.setField(priceUnderwriting, "carRentPriceCalculators", carRentPriceCalculators);
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementPrice() {
        when(carRentPriceCalculator1.getCarIc()).thenReturn("CAR_OPTIMUM");
        when(carRentPriceCalculator1.calculatePrice(any())).thenReturn(BigDecimal.ONE);

        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_OPTIMUM"));
        BigDecimal price = priceUnderwriting.calculatePrice(request);
        assertEquals(price, BigDecimal.ONE);
    }
}