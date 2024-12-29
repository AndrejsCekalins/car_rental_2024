package org.rental.core.underwriting;


import org.rental.dto.CarRentPriceCalculationRequest;

public interface CarRentPriceUnderwriting {

   CarRentPriceCalculationResult calculatePrice(CarRentPriceCalculationRequest request);
}
