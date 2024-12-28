package org.rental.core.services;

import org.rental.core.underwriting.CarRentPriceCalculationResult;
import org.rental.core.underwriting.CarRentPriceUnderwriting;
import org.rental.core.validations.CarRentPriceCalculationRequestValidator;
import org.rental.dto.CarRentPrice;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarRentPriceCalculationServiceImpl implements CarRentPriceCalculationService {

    private final CarRentPriceCalculationRequestValidator requestValidator;
    private final CarRentPriceUnderwriting priceUnderwriting;

    CarRentPriceCalculationServiceImpl(CarRentPriceCalculationRequestValidator requestValidator,
                                       CarRentPriceUnderwriting priceUnderwriting) {
        this.requestValidator = requestValidator;
        this.priceUnderwriting = priceUnderwriting;
    }

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

    private CarRentPriceCalculationResponse buildResponse(CarRentPriceCalculationRequest request,
                                                          CarRentPriceCalculationResult priceCalculationResult) {
        CarRentPriceCalculationResponse response = new CarRentPriceCalculationResponse();
        response.setPersonFirstName(request.getPersonFirstName());
        response.setPersonLastName(request.getPersonLastName());
        response.setAgreementDateFrom(request.getAgreementDateFrom());
        response.setAgreementDateTo(request.getAgreementDateTo());
        response.setAgreementTotalPrice(priceCalculationResult.getTotalPrice());
        response.setCarsForRent(priceCalculationResult.getCarRentPrices());
        return response;
    }

   /* private List<CarRentPrice> buildCarsForRent( CarRentPriceCalculationRequest request) {
        return request.getSelectedCar().stream()
                .map(carIc -> new CarRentPrice(carIc, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    */
}
