package org.rental.core;

import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CarRentPriceUnderwriting {

    @Autowired
    private DateTimeService dateTimeService;

    BigDecimal calculatePrice(CarRentPriceCalculationRequest request) {
        var daysBetween = dateTimeService.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }
}
