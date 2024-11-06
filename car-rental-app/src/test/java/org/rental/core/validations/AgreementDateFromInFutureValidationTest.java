package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rental.core.DateTimeService;
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
public class AgreementDateFromInFutureValidationTest {

    @Mock
    private DateTimeService dateTimeService;

    @Mock
    private ErrorCodeUnit errorCodeUnit;

    @InjectMocks
    private AgreementDateFromInFutureValidation validate;

    @Test
    public void shouldReturnErrorWhenAgreementDateFromInThePast() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2020"));
        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2026"));
        when(dateTimeService.getCurrentDateTime()).thenReturn(createDate("31.12.2025"));
        when(errorCodeUnit.getErrorDescription("ERROR_CODE_1")).thenReturn("Field agreementDateFrom must be in the future!");
        Optional<ValidationError> error =validate.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_1");
        assertEquals(error.get().getDescription(), "Field agreementDateFrom must be in the future!");
    }

    @Test
    public void shouldNotReturnErrorWhenAgreementDateFromNotInThePast() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2026"));
        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2026"));
        when(dateTimeService.getCurrentDateTime()).thenReturn(createDate("31.12.2025"));
        Optional<ValidationError> error =validate.execute(request);
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
