package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rental.core.ErrorCodeUnit;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmptySelectedVehicleValidationTest {

    @Mock
    private ErrorCodeUnit errorCodeUnit;

    @InjectMocks
    private EmptySelectedVehicleValidation validation;

@Test
    public void shouldReturnErrorWhenSelectedVehicleIsNull() {
    CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
    when(request.getSelectedVehicle()).thenReturn(null);
    when(errorCodeUnit.getErrorDescription("ERROR_CODE_6")).thenReturn("Field selectedVehicle must not be empty!");
    Optional<ValidationError>errorOpt = validation.execute(request);
    assertFalse(errorOpt.isEmpty());
    assertEquals(errorOpt.get().getErrorCode(), "ERROR_CODE_6");
    assertEquals(errorOpt.get().getDescription(), "Field selectedVehicle must not be empty!");
}

    @Test
    public void shouldReturnErrorWhenSelectedVehicleIsEmpty() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedVehicle()).thenReturn(List.of());
        when(errorCodeUnit.getErrorDescription("ERROR_CODE_6")).thenReturn("Field selectedVehicle must not be empty!");
        Optional<ValidationError>errorOpt = validation.execute(request);
        assertFalse(errorOpt.isEmpty());
        assertEquals(errorOpt.get().getErrorCode(), "ERROR_CODE_6");
        assertEquals(errorOpt.get().getDescription(), "Field selectedVehicle must not be empty!");
    }
    @Test
    public void shouldReturnErrorWhenSelectedVehicleIsPresent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedVehicle()).thenReturn(List.of("CAR_PREMIUM"));
        Optional<ValidationError>errorOpt = validation.execute(request);
        assertTrue(errorOpt.isEmpty());
    }
}