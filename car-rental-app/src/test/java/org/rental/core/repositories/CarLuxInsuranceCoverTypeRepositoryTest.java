package org.rental.core.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.CarLuxInsuranceCoverType;
import org.rental.core.domain.CountryDefaultDayRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CarLuxInsuranceCoverTypeRepositoryTest {

    @Autowired CarLuxInsuranceCoverTypeRepository carLuxInsuranceCoverTypeRepository;

    @Test
    public void injectedRepositoryAreNotNull() {assertNotNull(carLuxInsuranceCoverTypeRepository);}

    @Test
    public void shouldFindSmartInsurance() {
        searchForCarLuxInsuranceCoverType("SMART_INSURANCE",
                new BigDecimal("1.00"));
    }

    @Test
    public void shouldFindFullInsurance() {
        searchForCarLuxInsuranceCoverType("FULL_INSURANCE",
                new BigDecimal("1.20"));
    }

    @Test
    public void shouldFindPlatinumInsurance() {
        searchForCarLuxInsuranceCoverType("PLATINUM_INSURANCE",
                new BigDecimal("1.50"));
    }

    @Test
    public void shouldNotFindForUnknownCarLuxInsuranceCoverType() {
        Optional<CarLuxInsuranceCoverType> valueOpt = carLuxInsuranceCoverTypeRepository.findByCarLuxInsuranceCoverTypeIc("FAKE_INSURANCE");
        assertTrue(valueOpt.isEmpty());
    }

    private void searchForCarLuxInsuranceCoverType(String carLuxInsuranceCoverTypeIc, BigDecimal coefficient) {
        Optional<CarLuxInsuranceCoverType> valueOpt = carLuxInsuranceCoverTypeRepository.findByCarLuxInsuranceCoverTypeIc(carLuxInsuranceCoverTypeIc);
        assertTrue(valueOpt.isPresent());
        assertEquals(valueOpt.get().getCarLuxInsuranceCoverTypeIc(), carLuxInsuranceCoverTypeIc);
        assertEquals(valueOpt.get().getCoefficient(), coefficient);

        assertEquals(coefficient.stripTrailingZeros(),
                valueOpt.get().getCoefficient().stripTrailingZeros());
    }

}