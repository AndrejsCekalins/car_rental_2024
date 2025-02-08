package org.rental.core.validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCarLuxInsuranceCoverValidationTest {

    @Mock private ValidationErrorFactory errorFactory;

    @InjectMocks private EmptyCarLuxInsuranceCoverValidation validation;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp() {request = new CarRentPriceCalculationRequest();}

    @Test
    void shouldReturnValidationErrorWhenCarLuxInsuranceCoverEnabledAndNullOrBlank() {
        request.setSelectedCar(List.of("CAR_LUX"));
        request.setCarLuxInsuranceCoverType(null);
        ValidationError expectedError =mock(ValidationError.class);
        when(errorFactory.buildError("ERROR_CODE_13")).thenReturn(expectedError);

        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", true);

        Optional<ValidationError> result = validation.validate(request);

        assertTrue(result.isPresent());
        assertEquals(expectedError, result.get());

    }

    @Test
    void shouldNotReturnValidationErrorWhenCarLuxInsuranceCoverEnabledAndIsNotBlank() {
        request.setSelectedCar(List.of("CAR_LUX"));
        request.setCarLuxInsuranceCoverType("SMART_INSURANCE");

        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", true);

        Optional<ValidationError> result = validation.validate(request);

        assertTrue(result.isEmpty());


    }

    @Test
    void shouldNotReturnValidationErrorWhenCarLuxInsuranceCoverNotEnabledAndIsBlank() {
        request.setSelectedCar(List.of("CAR_LUX"));
        request.setCarLuxInsuranceCoverType("");

        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", false);

        Optional<ValidationError> result = validation.validate(request);

        assertTrue(result.isEmpty());


    }
}