package org.rental.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rental.core.util.BigDecimalSerializer;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarRentPrice {

    private String carIc;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal price;

}
