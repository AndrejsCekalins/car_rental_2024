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

    private final List<CarRentPriceCalculator> carRentPriceCalculators;

    @Override
    public CarRentPriceCalculationResult calculatePrice(CarRentPriceCalculationRequest request) {
        List<CarRentPrice>carRentPrices = request.getSelectedCar().stream()
                .map(carIc-> {
                        BigDecimal carRentPrice = calculatePriceForCar(carIc, request);
                        return new CarRentPrice(carIc, carRentPrice);
                })
                .toList();

        BigDecimal totalPrice =carRentPrices.stream()
                .map(CarRentPrice::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CarRentPriceCalculationResult(totalPrice, carRentPrices);
    }

    private BigDecimal calculatePriceForCar( String carIc, CarRentPriceCalculationRequest request) {
        var carRentPriceCalculator =findCarRentPriceCalculator(carIc);
        return carRentPriceCalculator.calculatePrice(request);

    }

    private CarRentPriceCalculator findCarRentPriceCalculator (String carIc) {
        return carRentPriceCalculators.stream()
                .filter(carRentCalculator ->carRentCalculator.getCarIc().equals(carIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported carIc = " + carIc));
    }

}

