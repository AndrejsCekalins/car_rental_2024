package org.rental.core.services;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;

public interface CarRentPriceCalculationService {

    CarRentPriceCalculationResponse calculatePrice(CarRentPriceCalculationRequest request);
}
