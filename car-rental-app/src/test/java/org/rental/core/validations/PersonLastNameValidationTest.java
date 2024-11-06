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
class PersonLastNameValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonLastNameValidation validation;

    @Test
    public void shouldReturnErrorWhenPersonLastNameIsEmpty() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonLastName()).thenReturn("");
        when(errorFactory.buildError("ERROR_CODE_8")).thenReturn(new ValidationError("ERROR_CODE_8", "Field personLastName must not be empty!"));
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_8");
        assertEquals(error.get().getDescription(), "Field personLastName must not be empty!");
    }

    @Test
    public void shouldReturnErrorWhenPersonLastNameIsNull() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonLastName()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_8")).thenReturn(new ValidationError("ERROR_CODE_8", "Field personLastName must not be empty!"));
        Optional<ValidationError> error = validation.execute(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_8");
        assertEquals(error.get().getDescription(), "Field personLastName must not be empty!");
    }

    @Test
    public void shouldNotReturnErrorWhenPersonLastNameIsPresent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonLastName()).thenReturn("lastName");
        Optional<ValidationError> error = validation.execute(request);
        assertTrue(error.isEmpty());

    }
}