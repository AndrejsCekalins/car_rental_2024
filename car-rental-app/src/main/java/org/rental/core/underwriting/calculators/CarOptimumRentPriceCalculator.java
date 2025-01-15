package org.rental.core.underwriting.calculators;

import lombok.AllArgsConstructor;
import org.rental.core.domain.CountryDefaultDayRate;
import org.rental.core.repositories.CountryDefaultDayRateRepository;
import org.rental.core.underwriting.CarRentPriceCalculator;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@Component
class CarOptimumRentPriceCalculator implements CarRentPriceCalculator {

    @Autowired
    private final DateTimeUtil dateTimeService;
    @Autowired
    private  final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request)
    {
        var daysCount =calculateDayCount(request);
        var countryDefaultRate = findCountryDefaultDayRate(request);
        return countryDefaultRate.multiply(daysCount)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDayCount(CarRentPriceCalculationRequest request) {
        var daysBetween = dateTimeService.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }

    private BigDecimal findCountryDefaultDayRate(CarRentPriceCalculationRequest request) {
        return countryDefaultDayRateRepository.findByCountryIc(request.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(()-> new RuntimeException("Country day rate not found by country id = " + request.getCountry()));
    }

    @Override
    public String getCarIc() {return "CAR_OPTIMUM";}
}
