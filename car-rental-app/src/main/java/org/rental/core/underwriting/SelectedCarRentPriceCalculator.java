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
public class SelectedCarRentPriceCalculator {

    private final List<CarRentPriceCalculator> carRentPriceCalculators;

    public List<CarRentPrice> calculatePriceForAllCarsRent(CarRentPriceCalculationRequest request) {
        return request.getSelectedCar().stream()
                .map(carIc -> new CarRentPrice(carIc, calculatePriceForCar(carIc, request)))
                .toList();
    }
    private BigDecimal calculatePriceForCar( String carIc, CarRentPriceCalculationRequest request) {
        return findCarRentPriceCalculator(carIc).calculatePrice(request);
    }

    private CarRentPriceCalculator findCarRentPriceCalculator (String carIc) {
        return carRentPriceCalculators.stream()
                .filter(carRentCalculator ->carRentCalculator.getCarIc().equals(carIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported carIc = " + carIc));
    }

}
