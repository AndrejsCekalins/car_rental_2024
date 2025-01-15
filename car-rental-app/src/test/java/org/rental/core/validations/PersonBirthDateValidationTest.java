package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class PersonBirthDateValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonBirthDateValidation validation;

    @Test
    public void shouldReturnErrorWhenPersonsBirthDateIsNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);;
        when(request.getPersonBirthDate()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_11")).thenReturn(new ValidationError("ERROR_CODE_11", "Field personBirthDate must be in the empty!"));
        Optional<ValidationError> error = validation.validate(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_11");
        assertEquals(error.get().getDescription(), "Field personBirthDate must be in the empty!");
    }

    @Test
    public void shouldNotReturnErrorWhenPersonsBirthDateIsNotNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);;
        when(request.getPersonBirthDate()).thenReturn(createDate("20.12.1998"));
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