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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarLuxInsuranceCoverTypeValidationTest {

    @Mock
    private ClassifierValueRepository classifierValueRepository;
    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private CarLuxInsuranceCoverTypeValidation validation;


    @Test
    public void shouldNotReturnErrorWhenCarLuxInsuranceCoverTypeNotEnable(){
        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", Boolean.FALSE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenNotContainSelectedCarLux(){
        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", Boolean.TRUE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_OPTIMUM"));
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenCarLuxInsuranceCoverTypeIsNull(){
        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", Boolean.TRUE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_LUX"));
        when(request.getCarLuxInsuranceCoverType()).thenReturn(null);
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenCarLuxInsuranceCoverTypeIsBlank(){
        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", Boolean.TRUE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_LUX"));
        when(request.getCarLuxInsuranceCoverType()).thenReturn("");
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenCarLuxInsuranceCoverTypeExistInDb(){
        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", Boolean.TRUE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_LUX"));
        when(request.getCarLuxInsuranceCoverType()).thenReturn("SMART_INSURANCE");
        ClassifierValue classifierValue = mock(ClassifierValue.class);
        when(classifierValueRepository.findByClassifierTitleAndIc("CAR_LUX_INSURANCE_COVER_TYPE", "SMART_INSURANCE"))
                .thenReturn(Optional.of(classifierValue));
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @Test
    public void shouldReturnError() {
        ReflectionTestUtils.setField(validation, "carLuxInsuranceCoverTypeEnabled", Boolean.TRUE);
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getSelectedCar()).thenReturn(List.of("CAR_LUX"));
        when(request.getCarLuxInsuranceCoverType()).thenReturn("SMART_INSURANCE");
        when(classifierValueRepository.findByClassifierTitleAndIc("CAR_LUX_INSURANCE_COVER_TYPE", "SMART_INSURANCE"))
                .thenReturn(Optional.empty());
        ValidationError validationError = mock(ValidationError.class);
        when(errorFactory.buildError("ERROR_CODE_14")).thenReturn(validationError);
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationError, validationErrorOpt.get());

    }




}