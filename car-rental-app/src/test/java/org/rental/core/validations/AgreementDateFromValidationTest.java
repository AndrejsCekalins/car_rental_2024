package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AgreementDateFromValidationTest {

    private AgreementDateFromValidation validation = new AgreementDateFromValidation();

    @Test
    public void shouldReturnErrorWhenAgreementDateFromIsNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(null);
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getField(), "agreementDateFrom");
        assertEquals(error.get().getMessage(), "Must not be empty!");
    }

    @Test
    public void shouldNotReturnErrorWhenAgreementDateFromIsPresent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getAgreementDateFrom()).thenReturn(createDate("31.12.2026"));
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