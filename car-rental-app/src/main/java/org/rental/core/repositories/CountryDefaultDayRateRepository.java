package org.rental.core.repositories;

import org.rental.core.domain.CountryDefaultDayRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryDefaultDayRateRepository
        extends JpaRepository<CountryDefaultDayRate, Long> {

    Optional<CountryDefaultDayRate> findByCountryIc(String countryIc);

}
