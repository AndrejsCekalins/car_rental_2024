package org.rental.core.underwriting;


import org.rental.dto.CarRentPriceCalculationRequest;

public interface CarRentPriceUnderwriting {

   CarRentPriceCalculationResult calculateAgreementPrice(CarRentPriceCalculationRequest request);
}
