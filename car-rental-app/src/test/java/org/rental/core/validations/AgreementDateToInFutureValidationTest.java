package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.DateTimeService;
import org.rental.core.ErrorCodeUnit;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateToInFutureValidationTest {

    @Mock private DateTimeService dateTimeService;

    @Mock private ErrorCodeUnit errorCodeUnit;

    @InjectMocks private AgreementDateToInFutureValidation validation;

    @Test
    public void shouldReturnErrorWhenAgreementDateToInThePast() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2020"));
        when(dateTimeService.getCurrentDateTime()).thenReturn(createDate("01.01.2025"));
        when(errorCodeUnit.getErrorDescription("ERROR_CODE_3")).thenReturn("Field agreementDateTo must be in the future!");
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_3");
        assertEquals(error.get().getDescription(), "Field agreementDateTo must be in the future!");
    }

    @Test
    public void shouldNotReturnErrorWhenAgreementDateToNotInThePast() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2026"));
        when(dateTimeService.getCurrentDateTime()).thenReturn(createDate("01.01.2025"));
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