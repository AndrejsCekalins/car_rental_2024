package org.rental.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rental.dto.CarRentPriceCalculationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CarRentPriceCalculationResponseLogger {

    private static final Logger logger = LoggerFactory.getLogger(CarRentPriceCalculationResponseLogger.class);

    void log (CarRentPriceCalculationResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(response);
            logger.info("RESPONSE: "+json);
        } catch (JsonProcessingException e) {
            logger.error("Error to convert response to JSON", e);
        }
    }

}
