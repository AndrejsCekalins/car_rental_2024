package org.rental.core.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rental.core.domain.CountryDefaultDayRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CountryDefaultDayRateRepositoryTest {

    @Autowired
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Test
    public void injectedRepositoryAreNotNull() {
        assertNotNull(countryDefaultDayRateRepository);
    }

    @Test
    public void shouldFindForLatvia() {
        searchCountryDefaultDayRate("LATVIA", new BigDecimal("1.00"));
    }

    @Test
    public void shouldFindForSpain() {
        searchCountryDefaultDayRate("SPAIN", new BigDecimal("2.50"));
    }

    @Test
    public void shouldFindForGermany() {
        searchCountryDefaultDayRate("GERMANY", new BigDecimal("3.50"));
    }

    @Test
    public void shouldNotFindForUnknownCountry() {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository.findByCountryIc("FAKE_COUNTRY");
        assertTrue(valueOpt.isEmpty());
    }


    private void searchCountryDefaultDayRate(String countryIc, BigDecimal dayRate) {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository.findByCountryIc(countryIc);
        assertTrue(valueOpt.isPresent());
        assertEquals(valueOpt.get().getCountryIc(), countryIc);
        assertEquals(valueOpt.get().getDefaultDayRate(), dayRate);

        assertEquals(dayRate.stripTrailingZeros(),
                valueOpt.get().getDefaultDayRate().stripTrailingZeros());
    }
}