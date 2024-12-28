package org.rental.core.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rental.core.domain.Classifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassifierRepositoryTest {

    @Autowired private ClassifierRepository classifierRepository;

    @Test
    public void injectedRepositoryAreNotNull() {
        assertNotNull(classifierRepository);
    }
    @Test
    public void shouldFindVehicleTypeClassifier() {
        Optional<Classifier> carTypeOpt = classifierRepository.findByTitle("CAR_TYPE");
        assertTrue(carTypeOpt.isPresent());
        assertEquals(carTypeOpt.get().getTitle(), "CAR_TYPE");
    }

    @Test
    public void shouldNotFindFakeClassifier() {
        Optional<Classifier> vehicleTypeOpt = classifierRepository.findByTitle("FAKE");
        assertTrue(vehicleTypeOpt.isEmpty());
    }
}