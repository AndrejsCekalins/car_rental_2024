package org.rental.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.rental.dto.CarRentPrice;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CarRentPriceUnderwritingImpl implements CarRentPriceUnderwriting {

    final private SelectedCarRentPriceCalculator selectedCarRentPriceCalculator;

    @Override
    public CarRentPriceCalculationResult calculateAgreementPrice(CarRentPriceCalculationRequest request) {
        List<CarRentPrice> carRentPrices = calculatePriceForSelectedCarsRent(request);
        BigDecimal totalPrice = calculateTotalPrice(carRentPrices);
        return new CarRentPriceCalculationResult(totalPrice, carRentPrices);
    }

    private List<CarRentPrice> calculatePriceForSelectedCarsRent(CarRentPriceCalculationRequest request){
        return selectedCarRentPriceCalculator.calculatePriceForAllCarsRent(request);
    }

    private  static BigDecimal calculateTotalPrice(List<CarRentPrice> carRentPrices) {
        return carRentPrices.stream()
        .map(CarRentPrice::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}

