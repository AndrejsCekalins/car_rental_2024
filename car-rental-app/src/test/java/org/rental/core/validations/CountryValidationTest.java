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
class CountryValidationTest {

    @Mock
    private ClassifierValueRepository classifierValueRepository;
    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private CountryValidation validation;


    @Test
    public void shouldNotReturnErrorWhenCountryExistInDb(){
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getCountry()).thenReturn("SPAIN");

        ClassifierValue classifierValue = mock(ClassifierValue.class);
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "SPAIN"))
                .thenReturn(Optional.of(classifierValue));

        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @Test
    public void shouldReturnError() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getCountry()).thenReturn("USA");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "USA"))
                .thenReturn(Optional.empty());
        ValidationError validationError = mock(ValidationError.class);
        when(errorFactory.buildError("ERROR_CODE_15")).thenReturn(validationError);
        Optional<ValidationError> validationErrorOpt =validation.validate(request);
        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationError, validationErrorOpt.get());

    }

}