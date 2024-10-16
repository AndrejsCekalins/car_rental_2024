package org.rental.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;
import org.rental.dto.ValidationError;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRentPriceCalculationServiceImplTest {

    @Mock private CarRentPriceCalculationRequestValidator requestValidator;
    @Mock private CarRentPriceUnderwriting priceUnderwriting;

    @InjectMocks
    private CarRentPriceCalculationServiceImpl service;

    @Test
    public void shouldReturnResponseWithErrors() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertTrue(response.hasErrors());
    }

    @Test
    public void shouldReturnResponseWithValidationErrors() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertEquals(response.getErrors().size(), 1);
        assertEquals(response.getErrors().get(0).getField(), "field");
        assertEquals(response.getErrors().get(0).getMessage(), "errorMessage");

    }

    @Test
    public void shouldNotInvokeCarRentPriceUnderwritingWhenValidationErrors() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertEquals(response.getErrors().size(), 1);
        assertEquals(response.getErrors().get(0).getField(), "field");
        assertEquals(response.getErrors().get(0).getMessage(), "errorMessage");
        verifyNoInteractions(priceUnderwriting);
        ;
    }

    @Test
    public void shouldReturnResponseWithCorrectPersonFirstName() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getPersonFirstName()).thenReturn("personFirstName");
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getPersonFirstName(), "personFirstName");
    }

    @Test
    public void shouldReturnResponseWithCorrectPersonLastName() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getPersonLastName()).thenReturn("personLastName");
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getPersonLastName(), "personLastName");
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementDateFrom() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        Date dateFrom = new Date();
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getAgreementDateFrom()).thenReturn(dateFrom);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getAgreementDateFrom(), dateFrom);
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementDateTo() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        Date dateTo = new Date();
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getAgreementDateTo()).thenReturn(dateTo);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getAgreementDateTo(), dateTo);
    }

    @Test
    public void shouldReturnResponseWithCorrectAgreementPrice() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getAgreementDateFrom()).thenReturn(createDate("01.01.2024"));
        when(request.getAgreementDateTo()).thenReturn(createDate("10.01.2024"));
        when(priceUnderwriting.calculatePrice(request)).thenReturn(new BigDecimal(9L));
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getAgreementPrice(), new BigDecimal(9));
    }

    private List<ValidationError> buildValidationErrorList() {
        return List.of(
                new ValidationError("field", "errorMessage"));
    }

    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
