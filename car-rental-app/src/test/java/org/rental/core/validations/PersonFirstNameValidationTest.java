package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PersonFirstNameValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonFirstNameValidation validation;

    @Test
    public void shouldReturnErrorWhenPersonFirstNameIsNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonFirstName()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_7")).thenReturn(new ValidationError("ERROR_CODE_7", "Field personFirstName must not be empty!"));
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_7");
        assertEquals(error.get().getDescription(), "Field personFirstName must not be empty!");
    }

    @Test
    public void shouldReturnErrorWhenPersonFirstNameIsEmpty() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonFirstName()).thenReturn("");
        when(errorFactory.buildError("ERROR_CODE_7")).thenReturn(new ValidationError("ERROR_CODE_7", "Field personFirstName must not be empty!"));
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_7");
        assertEquals(error.get().getDescription(), "Field personFirstName must not be empty!");
    }

    @Test
    public void shouldNotReturnErrorWhenPersonFirstNameIsPresent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonFirstName()).thenReturn("firstName");
        Optional<ValidationError> error = validation.execute(request);
        assertTrue(error.isEmpty());
    }

}