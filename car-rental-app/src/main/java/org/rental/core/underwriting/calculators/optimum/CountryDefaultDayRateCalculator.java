package org.rental.core.underwriting.calculators.optimum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.rental.core.domain.CountryDefaultDayRate;
import org.rental.core.repositories.CountryDefaultDayRateRepository;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CountryDefaultDayRateCalculator {

    private  final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    BigDecimal calculate(CarRentPriceCalculationRequest request) {
        return countryDefaultDayRateRepository.findByCountryIc(request.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(()-> new RuntimeException("Country day rate not found by country id = " + request.getCountry()));
    }
}
