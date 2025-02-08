package org.rental.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "car_lux_insurance_cover_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarLuxInsuranceCoverType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_lux_insurance_cover_type_ic", nullable = false)
    private String carLuxInsuranceCoverTypeIc;

    @Column(name = "coefficient", precision = 10, scale = 2, nullable = false)
    private BigDecimal coefficient;
}
