package org.rental.core.underwriting.calculators.lux;

import lombok.AllArgsConstructor;
import org.rental.core.domain.CarLuxInsuranceCoverType;
import org.rental.core.underwriting.CarRentPriceCalculator;
import org.rental.core.underwriting.calculators.supportive.AgeCoefficientCalculator;
import org.rental.core.underwriting.calculators.supportive.CountryDefaultDayRateCalculator;
import org.rental.core.underwriting.calculators.supportive.DayCountCalculator;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@Component
class CarLuxRentPriceCalculator implements CarRentPriceCalculator {

    private final DayCountCalculator dayCountCalculator;
    private final AgeCoefficientCalculator ageCoefficientCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    private final CarLuxInsuranceCoverTypeCalculator carLuxInsuranceCoverTypeCalculator;

    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request) {
        var daysCount =dayCountCalculator.calculate(request);
        var countryDefaultRate = countryDefaultDayRateCalculator.calculate(request);
        var ageCoefficient = ageCoefficientCalculator.calculate(request);
        var insuranceType = carLuxInsuranceCoverTypeCalculator.calculate(request);
        return countryDefaultRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .multiply(insuranceType)
                .setScale(2, RoundingMode.HALF_UP);}


    @Override
    public String getCarIc() {return "CAR_LUX";}
}
