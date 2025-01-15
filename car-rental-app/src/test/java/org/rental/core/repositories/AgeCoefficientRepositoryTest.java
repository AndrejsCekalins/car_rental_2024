package org.rental.core.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rental.core.domain.AgeCoefficient;
import org.rental.core.domain.Classifier;
import org.rental.core.domain.CountryDefaultDayRate;
import org.rental.core.util.DateTimeUtil;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AgeCoefficientRepositoryTest {

    @Autowired
    private AgeCoefficientRepository ageCoefficientRepository;


    @Test
    public void injectedRepositoryAreNotNull() {
        assertNotNull(ageCoefficientRepository);
    }

    @Test
    public void shouldFindAgeCoefficient_18() {
        searchAgeCoefficient(18, new BigDecimal("2.00"));
    }

    @Test
    public void shouldFindAgeCoefficient_26() {
        searchAgeCoefficient(26, new BigDecimal("2.00"));
    }

    @Test
    public void shouldFindAgeCoefficient_27() {
        searchAgeCoefficient(27, new BigDecimal("1.00"));;
    }

    @Test
    public void shouldFindAgeCoefficient_70() {
        searchAgeCoefficient(27, new BigDecimal("1.00"));;
    }

    @Test
    public void shouldFindAgeCoefficient_71() {
        searchAgeCoefficient(71, new BigDecimal("1.50"));
    }

    @Test
    public void shouldFindAgeCoefficient_150() {
        searchAgeCoefficient(150, new BigDecimal("1.50"));
    }

    @Test
    public void shouldNotFindForUnknownAge() {
        Optional<AgeCoefficient> valueOpt = ageCoefficientRepository.findCoefficient(0);
        assertTrue(valueOpt.isEmpty());
    }

    private void searchAgeCoefficient(Integer age,BigDecimal coefficient) {
        Optional<AgeCoefficient> valueOpt = ageCoefficientRepository.findCoefficient(age);
        assertTrue(valueOpt.isPresent());
        assertEquals(valueOpt.get().getCoefficient(), coefficient);

        assertEquals(coefficient.stripTrailingZeros(),
              valueOpt.get().getCoefficient().stripTrailingZeros());
    }

}