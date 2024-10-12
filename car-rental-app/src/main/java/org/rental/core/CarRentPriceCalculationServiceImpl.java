package org.rental.core;

import org.rental.rest.CarRentPriceCalculationRequest;
import org.rental.rest.CarRentPriceCalculationResponse;

public class CarRentPriceCalculationServiceImpl implements CarRentPriceCalculationService {
    @Override
    public CarRentPriceCalculationResponse calculatePrice(CarRentPriceCalculationRequest request) {
        CarRentPriceCalculationResponse response = new CarRentPriceCalculationResponse();
        response.setPersonFirstName(request.getPersonFirstName());
        response.setPersonLastName(request.getPersonLastName());
        response.setAgreementDateFrom(request.getAgreementDateFrom());
        response.setGetAgreementDateTo(request.getAgreementDateTo());
        return response;
    }
}
