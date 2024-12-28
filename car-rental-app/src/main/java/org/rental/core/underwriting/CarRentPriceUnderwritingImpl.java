package org.rental.core.underwriting;

import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class CarRentPriceUnderwritingImpl implements CarRentPriceUnderwriting {

    @Autowired
    private List<CarRentPriceCalculator> carRentPriceCalculators;

    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request) {
        return request.getSelectedCar().stream()
                .map(carIc -> calculatePriceForCar(carIc, request))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

