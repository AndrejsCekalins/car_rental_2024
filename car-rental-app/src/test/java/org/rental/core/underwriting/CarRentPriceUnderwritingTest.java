package org.rental.core.underwriting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.dto.CarRentPrice;
import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarRentPriceUnderwritingTest {

    @Mock
    private SelectedCarRentPriceCalculator selectedCarRentPriceCalculator;

    @InjectMocks
    private CarRentPriceUnderwritingImpl priceUnderwriting;


    @Test
    public void shouldCalculatePriceForOneCarForRent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);

        List<CarRentPrice> carRentPrices = List.of(new CarRentPrice ("CAR_PREMIUM", BigDecimal.ONE));

        when(selectedCarRentPriceCalculator.calculatePriceForAllCarsRent(request)).thenReturn(carRentPrices);

        CarRentPriceCalculationResult carRentPriceCalculationResult = priceUnderwriting.calculateAgreementPrice(request);

        assertEquals(carRentPriceCalculationResult.getTotalPrice(), BigDecimal.ONE);
    }

    @Test
    public void shouldCalculatePriceForTwoCarsForRent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);

        List<CarRentPrice> carRentPrices = List.of(
                new CarRentPrice ("CAR_PREMIUM", BigDecimal.ONE),
                new CarRentPrice("CAR_LUX", BigDecimal.ONE)
        );
        when(selectedCarRentPriceCalculator.calculatePriceForAllCarsRent(request)).thenReturn(carRentPrices);

        CarRentPriceCalculationResult carRentPriceCalculationResult = priceUnderwriting.calculateAgreementPrice(request);

        assertEquals(carRentPriceCalculationResult.getTotalPrice(), BigDecimal.TWO);
    }
}
