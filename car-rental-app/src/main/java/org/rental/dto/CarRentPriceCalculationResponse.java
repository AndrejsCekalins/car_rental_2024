package org.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarRentPriceCalculationResponse extends CoreResponse {

    private String personFirstName;
    private String personLastName;
    private Date agreementDateFrom;
    private Date getAgreementDateTo;

    private BigDecimal agreementPrice;

    public CarRentPriceCalculationResponse(List<ValidationError> errors) {super(errors);}
}
