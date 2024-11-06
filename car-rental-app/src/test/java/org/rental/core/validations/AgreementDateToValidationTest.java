package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rental.core.ErrorCodeUnit;
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
class AgreementDateToValidationTest {

    @Mock
    private ErrorCodeUnit errorCodeUnit;

    @InjectMocks
    private AgreementDateToValidation validation;

    @Test
    public void shouldReturnErrorWhenAgreementDateToIsNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);;
        when(request.getAgreementDateTo()).thenReturn(null);
        when(errorCodeUnit.getErrorDescription("ERROR_CODE_4")).thenReturn("Field agreementDateTo must not be empty!");
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_4");
        assertEquals(error.get().getDescription(), "Field agreementDateTo must not be empty!");
    }

    @Test
    public void shouldNotReturnErrorWhenAgreementDateToIsNotNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);;
        when(request.getAgreementDateTo()).thenReturn(createDate("31.12.2026"));
        Optional<ValidationError> error = validation.execute(request);
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