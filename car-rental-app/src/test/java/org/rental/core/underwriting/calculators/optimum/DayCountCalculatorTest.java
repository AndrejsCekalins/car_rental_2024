package org.rental.core.underwriting.calculators.optimum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DayCountCalculatorTest {

   @Mock
   private DateTimeUtil dateTimeUtil;


    @InjectMocks
    private DayCountCalculator calculator;

    private CarRentPriceCalculationRequest request;

    @BeforeEach
    void setUp() {
        request = new CarRentPriceCalculationRequest();
        request.setAgreementDateFrom(createDate("20.01.2025"));
        request.setAgreementDateTo(createDate("30.01.2025"));

    }

    @Test
    void shouldBeCorrectDayCount() {
        long expectedDays = 10;

        when(dateTimeUtil.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo())).
                thenReturn(expectedDays);

        BigDecimal result = calculator.calculate(request);

        Assertions.assertEquals(BigDecimal.valueOf(expectedDays),result);
    }


    private Date createDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}