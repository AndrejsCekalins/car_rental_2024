package org.rental.core.validations;

import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.rental.core.repositories.ClassifierValueRepository;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CountryValidation extends CarRentRequestValidationImpl {

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(CarRentPriceCalculationRequest request) {
        return isCountryNotBlank(request)
                && !existInDatabase(request.getCountry())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }

    private boolean isCountryNotBlank(CarRentPriceCalculationRequest request){
        return request.getCountry() != null && !request.getCountry().isBlank();
    }

    private boolean existInDatabase(String countryIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", countryIc).isPresent();
    }






}
