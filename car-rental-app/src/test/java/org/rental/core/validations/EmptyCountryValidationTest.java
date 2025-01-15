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
class EmptyCountryValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyCountryValidation validation;

    @Test
    public void shouldReturnErrorWhenCountryIsNull(){
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getCountry()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_10")).thenReturn( new ValidationError("ERROR_CODE_10", "Field country must not be empty!"));
        Optional<ValidationError> errorOpt = validation.validate(request);
        assertFalse(errorOpt.isEmpty());
        assertEquals(errorOpt.get().getErrorCode(), "ERROR_CODE_10");
        assertEquals(errorOpt.get().getDescription(), "Field country must not be empty!");
    }

    @Test
    public void shouldReturnErrorWhenCountryIsEmpty(){
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getCountry()).thenReturn("");
        when(errorFactory.buildError("ERROR_CODE_10")).thenReturn( new ValidationError("ERROR_CODE_10", "Field country must not be empty!"));
        Optional<ValidationError> errorOpt = validation.validate(request);
        assertFalse(errorOpt.isEmpty());
        assertEquals(errorOpt.get().getErrorCode(), "ERROR_CODE_10");
        assertEquals(errorOpt.get().getDescription(), "Field country must not be empty!");
    }

    @Test
    public void shouldNotReturnErrorWhenCountryIsPresent(){
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getCountry()).thenReturn("SPAIN");
        Optional<ValidationError> errorOpt = validation.validate(request);
        assertTrue(errorOpt.isEmpty());
    }






}