package org.rental.core.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.underwriting.CarRentPriceCalculationResult;
import org.rental.core.underwriting.CarRentPriceUnderwriting;
import org.rental.core.validations.CarRentPriceCalculationRequestValidator;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;
import org.rental.dto.ValidationError;

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
        assertEquals(response.getErrors().get(0).getErrorCode(), "ERROR_CODE");
        assertEquals(response.getErrors().get(0).getDescription(), "errorDescription");

    }

    @Test
    public void shouldNotInvokeCarRentPriceUnderwritingWhenValidationErrors() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        List<ValidationError> errors = buildValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertEquals(response.getErrors().size(), 1);
        assertEquals(response.getErrors().get(0).getErrorCode(), "ERROR_CODE");
        assertEquals(response.getErrors().get(0).getDescription(), "errorDescription");
        verifyNoInteractions(priceUnderwriting);
        ;
    }

    @Test
    public void shouldReturnResponseWithCorrectPersonFirstName() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getPersonFirstName()).thenReturn("personFirstName");
        CarRentPriceCalculationResult carRentPriceCalculationResult = mock(CarRentPriceCalculationResult.class);
        when(priceUnderwriting.calculateAgreementPrice(request)).thenReturn(carRentPriceCalculationResult);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getPersonFirstName(), "personFirstName");
    }

    @Test
    public void shouldReturnResponseWithCorrectPersonLastName() {
        CarRentPriceCalculationRequest request = mock(CarRentPriceCalculationRequest.class);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(request.getPersonLastName()).thenReturn("personLastName");
        CarRentPriceCalculationResult carRentPriceCalculationResult = mock(CarRentPriceCalculationResult.class);
        when(priceUnderwriting.calculateAgreementPrice(request)).thenReturn(carRentPriceCalculationResult);
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
        CarRentPriceCalculationResult carRentPriceCalculationResult = mock(CarRentPriceCalculationResult.class);
        when(priceUnderwriting.calculateAgreementPrice(request)).thenReturn(carRentPriceCalculationResult);
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
        CarRentPriceCalculationResult carRentPriceCalculationResult = mock(CarRentPriceCalculationResult.class);
        when(priceUnderwriting.calculateAgreementPrice(request)).thenReturn(carRentPriceCalculationResult);
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
        CarRentPriceCalculationResult carRentPriceCalculationResult = new CarRentPriceCalculationResult(new BigDecimal(9), null);
        when(priceUnderwriting.calculateAgreementPrice(request)).thenReturn(carRentPriceCalculationResult);
        CarRentPriceCalculationResponse response = service.calculatePrice(request);
        assertFalse(response.hasErrors());
        assertEquals(response.getAgreementTotalPrice(), new BigDecimal(9));
    }

    private List<ValidationError> buildValidationErrorList() {
        return List.of(
                new ValidationError("ERROR_CODE", "errorDescription"));
    }

    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
