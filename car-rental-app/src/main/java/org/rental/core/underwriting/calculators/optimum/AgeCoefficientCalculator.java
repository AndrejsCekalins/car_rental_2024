package org.rental.core.underwriting.calculators.optimum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.rental.core.domain.AgeCoefficient;
import org.rental.core.repositories.AgeCoefficientRepository;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AgeCoefficientCalculator {

    private final DateTimeUtil dateTimeUtil;
    private final AgeCoefficientRepository ageCoefficientRepository;


    BigDecimal calculate(CarRentPriceCalculationRequest request) {
        int age = calculatedAge(request);
        return ageCoefficientRepository.findCoefficient(age)
                .map(AgeCoefficient::getCoefficient)
                .orElseThrow(()-> new RuntimeException("Age coefficient not found for age = " + age));
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
}
