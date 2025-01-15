package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.ValidationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonBirthDateInFutureValidationTest {

    @Mock
    private DateTimeUtil dateTimeService;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonBirthDateInFutureValidation validation;

    @Test
    public void shouldReturnErrorWhenPersonBirthDateIsInTheFuture() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonBirthDate()).thenReturn(createDate("10.01.2050"));
        when(dateTimeService.getCurrentDateTime()).thenReturn(createDate("01.01.2025"));
        when(errorFactory.buildError("ERROR_CODE_12")).thenReturn(new ValidationError("ERROR_CODE_12","Field personBirthDate must not be in the future!"));
        Optional<ValidationError> error = validation.validate(request);
        assertFalse(error.isEmpty());
        assertEquals(error.get().getErrorCode(), "ERROR_CODE_12");
        assertEquals(error.get().getDescription(), "Field personBirthDate must not be in the future!");
    }

    @Test
    public void shouldNotReturnErrorWhenPersonBirthDateIsNotInTheFuture() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(request.getPersonBirthDate()).thenReturn(createDate("10.01.1998"));
        when(dateTimeService.getCurrentDateTime()).thenReturn(createDate("01.01.2025"));
        Optional<ValidationError> error = validation.validate(request);
        assertTrue(error.isEmpty());
    }

    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}