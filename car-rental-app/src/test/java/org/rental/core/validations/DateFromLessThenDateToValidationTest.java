package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DateFromLessThenDateToValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private DateFromLessThenDateToValidation validation;

    @Test
    public void shouldReturnErrorWhenDateFromIsAfterDateTo() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("05.01.2026"));
        when((request.getAgreementDateTo())).thenReturn(createDate("01.01.2026"));
        when(errorFactory.buildError("ERROR_CODE_5")).thenReturn( new ValidationError("ERROR_CODE_5", "Field agreementDateFrom must be less than agreementDateTo!"));
        Optional<ValidationError> error = validation.validate(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_5");
        assertEquals(error.get().getDescription(), "Field agreementDateFrom must be less than agreementDateTo!");
    }

    @Test
    public void shouldReturnErrorWhenDateFromIsEqualDateTo() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2026"));
        when((request.getAgreementDateTo())).thenReturn(createDate("01.01.2026"));
        when(errorFactory.buildError("ERROR_CODE_5")).thenReturn( new ValidationError("ERROR_CODE_5", "Field agreementDateFrom must be less than agreementDateTo!"));
        Optional<ValidationError> error = validation.validate(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_5");
        assertEquals(error.get().getDescription(), "Field agreementDateFrom must be less than agreementDateTo!");
    }

    @Test
    public void shouldNotReturnErrorWhenDateFromIsBeforeDateTo() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2026"));
        when((request.getAgreementDateTo())).thenReturn(createDate("05.01.2026"));
        Optional<ValidationError> error = validation.validate(request);
        assertTrue(error.isEmpty());
    }

    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}