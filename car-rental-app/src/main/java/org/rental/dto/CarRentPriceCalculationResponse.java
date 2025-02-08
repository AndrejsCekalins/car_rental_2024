package org.rental.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rental.core.util.BigDecimalSerializer;

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

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date personBirthDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date agreementDateFrom;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date agreementDateTo;

    private String country;

    private String carLuxInsuranceCoverType;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal agreementTotalPrice;

    private List<CarRentPrice> carsForRent;

    public CarRentPriceCalculationResponse(List<ValidationError> errors) {super(errors);}
}
