package org.rental.core.underwriting.calculators;

import lombok.AllArgsConstructor;
import org.rental.core.domain.AgeCoefficient;
import org.rental.core.domain.CountryDefaultDayRate;
import org.rental.core.repositories.AgeCoefficientRepository;
import org.rental.core.repositories.CountryDefaultDayRateRepository;
import org.rental.core.underwriting.CarRentPriceCalculator;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Component
class CarOptimumRentPriceCalculator implements CarRentPriceCalculator {

    @Autowired
    private final DateTimeUtil dateTimeUtil;
    @Autowired
    private  final CountryDefaultDayRateRepository countryDefaultDayRateRepository;
    @Autowired
    private final AgeCoefficientRepository ageCoefficientRepository;

    @Override
    public BigDecimal calculatePrice(CarRentPriceCalculationRequest request)
    {
        var daysCount =calculateDayCount(request);
        var countryDefaultRate = findCountryDefaultDayRate(request);
        var ageCoefficient = findAgeCoefficient(request);
        return countryDefaultRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDayCount(CarRentPriceCalculationRequest request) {
        var daysBetween = dateTimeUtil.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }

    private BigDecimal findCountryDefaultDayRate(CarRentPriceCalculationRequest request) {
        return countryDefaultDayRateRepository.findByCountryIc(request.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(()-> new RuntimeException("Country day rate not found by country id = " + request.getCountry()));
    }

    private Integer calculatedAge(CarRentPriceCalculationRequest request){
        LocalDate personBirthDate = toLocalDate(request.getPersonBirthDate());
        LocalDate currentDate = toLocalDate(dateTimeUtil.getCurrentDateTime());
        return Period.between(personBirthDate, currentDate).getYears();
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private BigDecimal findAgeCoefficient(CarRentPriceCalculationRequest request) {
        int age = calculatedAge(request);
        return ageCoefficientRepository.findCoefficient(age)
                .map(AgeCoefficient::getCoefficient)
                .orElseThrow(()-> new RuntimeException("Age coefficient not found for age = " + age));
    }

    @Override
    public String getCarIc() {return "CAR_OPTIMUM";}
}
