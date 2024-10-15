package org.rental.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarRentPriceCalculationResponse {

    private String personFirstName;
    private String personLastName;
    private Date agreementDateFrom;
    private Date getAgreementDateTo;

    private BigDecimal agreementPrice;

}
