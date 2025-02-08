package org.rental.core.underwriting.calculators.supportive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DayCountCalculatorImpl implements DayCountCalculator {

    private final DateTimeUtil dateTimeUtil;

    @Override
    public BigDecimal calculate(CarRentPriceCalculationRequest request) {
        var daysBetween = dateTimeUtil.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }
}
