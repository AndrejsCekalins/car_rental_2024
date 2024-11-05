package org.rental.core.validations;

import org.checkerframework.common.value.qual.EnsuresMinLenIf;
import org.junit.jupiter.api.Test;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmptySelectedVehicleValidationTest {

private EmptySelectedVehicleValidation validation = new EmptySelectedVehicleValidation();

@Test
    public void shouldReturnErrorWhenSelectedVehicleIsNull() {
    CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
    when(request.getSelectedVehicle()).thenReturn(null);
    Optional<ValidationError>errorOpt = validation.execute(request);
    assertFalse(errorOpt.isEmpty());
    assertEquals(errorOpt.get().getField(), "selectedVehicle");
    assertEquals(errorOpt.get().getMessage(), "Must not be empty!");
}

    @Test
    public void shouldReturnErrorWhenSelectedVehicleIsEmpty() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedVehicle()).thenReturn(List.of());
        Optional<ValidationError>errorOpt = validation.execute(request);
        assertFalse(errorOpt.isEmpty());
        assertEquals(errorOpt.get().getField(), "selectedVehicle");
        assertEquals(errorOpt.get().getMessage(), "Must not be empty!");
    }
    @Test
    public void shouldReturnErrorWhenSelectedVehicleIsPresent() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedVehicle()).thenReturn(List.of("CAR_PREMIUM"));
        Optional<ValidationError>errorOpt = validation.execute(request);
        assertTrue(errorOpt.isEmpty());
    }
}