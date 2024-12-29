package org.rental.core.underwriting.calculators;

import org.rental.core.underwriting.CarRentPriceCalculator;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CarOptimumRentPriceCalculator implements CarRentPriceCalculator {

    @Autowired
    private DateTimeUtil dateTimeService;

    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request)
    {
        var daysBetween = dateTimeService.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }

    @Override
    public String getCarIc() {return "CAR_OPTIMUM";}
}
