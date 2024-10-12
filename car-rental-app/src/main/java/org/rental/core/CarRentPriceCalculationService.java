package org.rental.core;

import org.rental.rest.CarRentPriceCalculationRequest;
import org.rental.rest.CarRentPriceCalculationResponse;

public interface CarRentPriceCalculationService {

    CarRentPriceCalculationResponse calculatePrice(CarRentPriceCalculationRequest request);
}
