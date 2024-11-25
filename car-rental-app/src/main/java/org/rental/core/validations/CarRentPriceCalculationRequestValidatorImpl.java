package org.rental.core.validations;

import org.rental.dto.ValidationError;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class CarRentPriceCalculationRequestValidatorImpl implements CarRentPriceCalculationRequestValidator{

    @Autowired List<CarRentRequestValidation> carRentValidations;


    @Override
    public List<ValidationError> validate(CarRentPriceCalculationRequest request) {
        List<ValidationError> singleErrors = collectSingleErrors(request);
        List<ValidationError> listErrors = collectListErrors(request);
        return concatenateLists(singleErrors,listErrors) ;
    }

    private List<ValidationError> collectSingleErrors(CarRentPriceCalculationRequest request) {
        return carRentValidations.stream()
                .map(validation ->validation.validate(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<ValidationError> collectListErrors(CarRentPriceCalculationRequest request) {
        return carRentValidations.stream()
                .map(validation ->validation.validateList(request))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<ValidationError> concatenateLists(List<ValidationError> singleErrors,
                                                   List<ValidationError> listErrors) {

        return Stream.concat(singleErrors.stream(), listErrors.stream())
                .collect(Collectors.toList());
    }
}
