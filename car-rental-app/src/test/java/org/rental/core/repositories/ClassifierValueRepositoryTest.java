package org.rental.core.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rental.core.domain.ClassifierValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassifierValueRepositoryTest {

    @Autowired private ClassifierValueRepository classifierValueRepository;

    @Test
    public void injectedRepositoryAreNotNull() {assertNotNull(classifierValueRepository);}

    @Test
    public void shouldFind_VehicleType_CAR_OPTIMUM(){
        searchClassifierValueAndCheck("VEHICLE_TYPE","CAR_OPTIMUM" );
    }

    @Test
    public void shouldFind_VehicleType_CAR_PREMIUM() {
        searchClassifierValueAndCheck("VEHICLE_TYPE", "CAR_PREMIUM");
    }

    @Test
    public void shouldFind_VehicleType_CAR_VAN() {
        searchClassifierValueAndCheck("VEHICLE_TYPE","CAR_VAN" );
    }

    @Test
    public void shouldFind_VehicleType_CAR_LUX() {
        searchClassifierValueAndCheck("VEHICLE_TYPE","CAR_LUX" );
    }

    @Test
    public void shouldFind_VehicleType_CAR_SPORT() {
        searchClassifierValueAndCheck("VEHICLE_TYPE","CAR_SPORT" );
    }

    @Test
    public void shouldFind_VehicleType_FAKE() {
        Optional<ClassifierValue> valueOpt = classifierValueRepository.findByClassifierTitleAndIc("VEHICLE_TYPE", "FAKE");
        assertTrue(valueOpt.isEmpty());
    }

    private void searchClassifierValueAndCheck(String classifierTitle, String ic){
        Optional<ClassifierValue> valueOpt =classifierValueRepository.findByClassifierTitleAndIc(classifierTitle,ic);
        assertTrue(valueOpt.isPresent());
        assertEquals(valueOpt.get().getClassifier().getTitle(), classifierTitle);
        assertEquals(valueOpt.get().getIc(), ic);
    }

}