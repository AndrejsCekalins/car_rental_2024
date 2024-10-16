package org.rental.core;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CarRentPriceCalculationServiceImpl implements CarRentPriceCalculationService {

    @Autowired
    private CarRentPriceCalculationRequestValidator requestValidator;
    @Autowired
    private CarRentPriceUnderwriting priceUnderwriting;

    @Override
    public CarRentPriceCalculationResponse calculatePrice(CarRentPriceCalculationRequest request) {
        List<ValidationError> errors = requestValidator.validate(request);
        return errors.isEmpty()
                ? buildResponse(request, priceUnderwriting.calculatePrice(request))
                : buildResponse(errors);
    }


    private CarRentPriceCalculationResponse buildResponse(List<ValidationError> errors) {
        return new CarRentPriceCalculationResponse(errors);
    }

    private CarRentPriceCalculationResponse buildResponse(CarRentPriceCalculationRequest request, BigDecimal price) {
        CarRentPriceCalculationResponse response = new CarRentPriceCalculationResponse();
        response.setPersonFirstName(request.getPersonFirstName());
        response.setPersonLastName(request.getPersonLastName());
        response.setAgreementDateFrom(request.getAgreementDateFrom());
        response.setGetAgreementDateTo(request.getAgreementDateTo());
        response.setAgreementPrice(price);
        return response;
    }
}
