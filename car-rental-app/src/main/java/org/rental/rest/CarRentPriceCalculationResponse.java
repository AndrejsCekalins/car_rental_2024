package org.rental.rest;

import java.math.BigDecimal;
import java.util.Date;

public class CarRentPriceCalculationResponse {

private String personFirstName;
private String personLastName;
private Date agreementDateFrom;
private Date getAgreementDateTo;

private BigDecimal agreementPrice;

    public CarRentPriceCalculationResponse() {
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public Date getAgreementDateFrom() {
        return agreementDateFrom;
    }

    public void setAgreementDateFrom(Date agreementDateFrom) {
        this.agreementDateFrom = agreementDateFrom;
    }

    public Date getGetAgreementDateTo() {
        return getAgreementDateTo;
    }

    public void setGetAgreementDateTo(Date getAgreementDateTo) {
        this.getAgreementDateTo = getAgreementDateTo;
    }

    public BigDecimal getAgreementPrice() {
        return agreementPrice;
    }

    public void setAgreementPrice(BigDecimal agreementPrice) {
        this.agreementPrice = agreementPrice;
    }
}
