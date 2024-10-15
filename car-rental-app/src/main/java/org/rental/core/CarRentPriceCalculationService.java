package org.rental.core;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;

public interface CarRentPriceCalculationService {

    CarRentPriceCalculationResponse calculatePrice(CarRentPriceCalculationRequest request);
}
