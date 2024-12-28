package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.ClassifierValue;
import org.rental.core.repositories.ClassifierValueRepository;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectedCarValidationTest {

    @Mock private ClassifierValueRepository classifierValueRepository;
    @Mock private ValidationErrorFactory errorFactory;

    @InjectMocks private SelectedCarValidation validation;

    @Test
    public void shouldNotValidateWhenSelectedRisksIsNull(){
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(null);
        assertTrue(validation.validateList(request).isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldValidateWithoutError(){
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_OPTIMUM"));
        when(classifierValueRepository.findByClassifierTitleAndIc("CAR_TYPE", "CAR_OPTIMUM")).
                thenReturn(Optional.of(mock(ClassifierValue.class)));
        assertTrue(validation.validateList(request).isEmpty());
    }

    @Test
    public void shouldValidateWithError() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_ECONOMY"));
        when(classifierValueRepository.findByClassifierTitleAndIc("CAR_TYPE", "CAR_ECONOMY")).
                thenReturn(Optional.empty());

        ValidationError error =mock(ValidationError.class);
        when(errorFactory.buildError(eq("ERROR_CODE_9"), anyList())).thenReturn(error);

        assertEquals(validation.validateList(request).size(), 1);

    }


}