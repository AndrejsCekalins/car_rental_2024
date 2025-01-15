package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmptySelectedCarValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptySelectedCarValidation validation;

@Test
    public void shouldReturnErrorWhenSelectedCarIsNull() {
    CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
    when(request.getSelectedCar()).thenReturn(null);
    when(errorFactory.buildError("ERROR_CODE_6")).thenReturn( new ValidationError("ERROR_CODE_6", "Field selectedCar must not be empty!"));
    Optional<ValidationError>errorOpt = validation.validate(request);
    assertFalse(errorOpt.isEmpty());
    assertEquals(errorOpt.get().getErrorCode(), "ERROR_CODE_6");
    assertEquals(errorOpt.get().getDescription(), "Field selectedCar must not be empty!");
}

    @Test
    public void shouldReturnErrorWhenSelectedCarIsEmpty() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of());
        when(errorFactory.buildError("ERROR_CODE_6")).thenReturn( new ValidationError("ERROR_CODE_6", "Field selectedCar must not be empty!"));
        Optional<ValidationError>errorOpt = validation.validate(request);
        assertFalse(errorOpt.isEmpty());
        assertEquals(errorOpt.get().getErrorCode(), "ERROR_CODE_6");
        assertEquals(errorOpt.get().getDescription(), "Field selectedCar must not be empty!");
    }
    @Test
    public void shouldNotReturnErrorWhenSelectedCarIsPresent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_PREMIUM"));
        Optional<ValidationError>errorOpt = validation.validate(request);
        assertTrue(errorOpt.isEmpty());
    }
}