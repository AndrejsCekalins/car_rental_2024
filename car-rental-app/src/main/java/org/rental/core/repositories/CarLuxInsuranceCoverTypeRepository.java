package org.rental.core.repositories;

import org.rental.core.domain.CarLuxInsuranceCoverType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarLuxInsuranceCoverTypeRepository
        extends JpaRepository <CarLuxInsuranceCoverType, Long> {

    Optional<CarLuxInsuranceCoverType> findByCarLuxInsuranceCoverTypeIc(String carLuxInsuranceCoverTypeIc );
}
