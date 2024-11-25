package org.rental.core.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.util.ErrorCodeUnit;
import org.rental.core.util.Placeholder;
import org.rental.dto.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationErrorFactoryTest {

    @Mock private ErrorCodeUnit errorCodeUnit;

    @InjectMocks private ValidationErrorFactory factory;

    @Test
    public void shouldReturnValidationErrorWithDescription() {
        when(errorCodeUnit.getErrorDescription("ERROR_CODE"))
                .thenReturn("error description");
        ValidationError error = factory.buildError("ERROR_CODE");
        assertEquals(error.getErrorCode(), "ERROR_CODE");
        assertEquals(error.getDescription(), "error description");
    }

    @Test
    public void shouldReturnValidationErrorWithDescriptionUsingPlaceholder() {
        Placeholder placeholder = new Placeholder("PLACEHOLDER", "AAA");
        when(errorCodeUnit.getErrorDescription("ERROR_CODE", List.of(placeholder)))
                .thenReturn("error AAA description");
        ValidationError error = factory.buildError("ERROR_CODE", List.of(placeholder));
        assertEquals(error.getErrorCode(), "ERROR_CODE");
        assertEquals(error.getDescription(), "error AAA description");
    }
}