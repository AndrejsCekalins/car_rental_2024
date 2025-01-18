package org.rental.core.underwriting.calculators.optimum;

import lombok.AllArgsConstructor;
import org.rental.core.underwriting.CarRentPriceCalculator;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@Component
public class CarOptimumRentPriceCalculator implements CarRentPriceCalculator {

    private final DayCountCalculator dayCountCalculator;
    private final AgeCoefficientCalculator ageCoefficientCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;


    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request)
    {
        var daysCount =dayCountCalculator.calculate(request);
        var countryDefaultRate = countryDefaultDayRateCalculator.calculate(request);
        var ageCoefficient = ageCoefficientCalculator.calculate(request);
        return countryDefaultRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCarIc() {return "CAR_OPTIMUM";}
}
