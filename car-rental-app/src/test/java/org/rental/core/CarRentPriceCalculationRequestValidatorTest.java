package org.rental.core;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.validations.CarRentRequestValidation;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarRentPriceCalculationRequestValidatorTest {

   @InjectMocks
   private CarRentPriceCalculationRequestValidator requestValidator;

    @Test
    public void shouldNotReturnErrors() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        CarRentRequestValidation validation1 = mock(CarRentRequestValidation.class);
        when(validation1.execute(request)).thenReturn(Optional.empty());
        CarRentRequestValidation validation2 = mock(CarRentRequestValidation.class);
        when(validation2.execute(request)).thenReturn(Optional.empty());
        List<CarRentRequestValidation> carRentValidations = List.of(
                validation1, validation2
        );
        ReflectionTestUtils.setField(requestValidator, "carRentValidations", carRentValidations);
        List<ValidationError> errors = requestValidator.validate(request);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnErrors() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        CarRentRequestValidation validation1 = mock(CarRentRequestValidation.class);
        when(validation1.execute(request)).thenReturn(Optional.of(new ValidationError()));
        CarRentRequestValidation validation2 = mock(CarRentRequestValidation.class);
        when(validation2.execute(request)).thenReturn(Optional.of(new ValidationError()));
        List<CarRentRequestValidation> carRentValidations = List.of(
                validation1, validation2
        );
        ReflectionTestUtils.setField(requestValidator, "carRentValidations", carRentValidations);
        List<ValidationError> errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(errors.size(), 2);
    }

}