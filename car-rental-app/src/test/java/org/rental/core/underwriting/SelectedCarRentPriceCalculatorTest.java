package org.rental.core.underwriting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.dto.CarRentPrice;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SelectedCarRentPriceCalculatorTest {

    @InjectMocks
    private SelectedCarRentPriceCalculator selectedCarRentPriceCalculator;

    private CarRentPriceCalculator carRentPriceCalculator1;
    private CarRentPriceCalculator carRentPriceCalculator2;

    @BeforeEach
    public void init(){
        carRentPriceCalculator1 = mock(CarRentPriceCalculator.class);
        carRentPriceCalculator2 = mock(CarRentPriceCalculator.class);
        var carRentPriceCalculators = List.of(carRentPriceCalculator1, carRentPriceCalculator2);
        ReflectionTestUtils.setField(selectedCarRentPriceCalculator, "carRentPriceCalculators", carRentPriceCalculators);
    }

    @Test
    void shouldCalculatePriceForOneCarRent() {
        when(carRentPriceCalculator1.getCarIc()).thenReturn("CAR_PREMIUM");
        when(carRentPriceCalculator1.calculatePrice(any())).thenReturn(BigDecimal.ONE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_PREMIUM"));
        List<CarRentPrice> carRentPrices = selectedCarRentPriceCalculator.calculatePriceForAllCarsRent(request);
        assertEquals(carRentPrices.size(), 1);
        assertEquals(carRentPrices.get(0).getCarIc(), "CAR_PREMIUM");
        assertEquals(carRentPrices.get(0).getPrice(), BigDecimal.ONE);
    }

    @Test
    void shouldCalculatePriceForTwoCarsRent() {
        when(carRentPriceCalculator1.getCarIc()).thenReturn("CAR_PREMIUM");
        when(carRentPriceCalculator1.calculatePrice(any())).thenReturn(BigDecimal.ONE);
        when(carRentPriceCalculator2.getCarIc()).thenReturn("CAR_LUX");
        when(carRentPriceCalculator2.calculatePrice(any())).thenReturn(BigDecimal.TWO);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_PREMIUM", "CAR_LUX"));
        List<CarRentPrice> carRentPrices = selectedCarRentPriceCalculator.calculatePriceForAllCarsRent(request);
        assertEquals(carRentPrices.size(), 2);
        assertEquals(carRentPrices.get(0).getCarIc(), "CAR_PREMIUM");
        assertEquals(carRentPrices.get(0).getPrice(), BigDecimal.ONE);
        assertEquals(carRentPrices.get(1).getCarIc(), "CAR_LUX");
        assertEquals(carRentPrices.get(1).getPrice(), BigDecimal.TWO);
    }

    @Test
    void shouldThrowExceptionWhenSelectedCarRentTypeNotSupported() {
        when(carRentPriceCalculator1.getCarIc()).thenReturn("CAR_PREMIUM");
        when(carRentPriceCalculator2.getCarIc()).thenReturn("CAR_LUX");

        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("NOT_SUPPORTED_CAR_TYPE"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> selectedCarRentPriceCalculator.calculatePriceForAllCarsRent(request));
        assertEquals(exception.getMessage(), "Not supported carIc = NOT_SUPPORTED_CAR_TYPE");
    }
}