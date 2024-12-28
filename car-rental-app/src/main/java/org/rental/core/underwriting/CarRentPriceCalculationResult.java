package org.rental.core.underwriting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rental.dto.CarRentPrice;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class CarRentPriceCalculationResult {

    private BigDecimal totalPrice;

    private List<CarRentPrice> carRentPrices;
}
