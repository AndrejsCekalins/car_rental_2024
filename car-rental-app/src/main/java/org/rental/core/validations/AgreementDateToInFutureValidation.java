package org.rental.core.validations;

import org.rental.core.DateTimeService;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class AgreementDateToInFutureValidation implements CarRentRequestValidation {

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> execute(CarRentPriceCalculationRequest request) {
        Date dateTo = request.getAgreementDateTo();
        Date currentDateTime = dateTimeService.getCurrentDateTime();
        return (dateTo != null && dateTo.before(currentDateTime))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_3"))
                : Optional.empty();
    }
}
