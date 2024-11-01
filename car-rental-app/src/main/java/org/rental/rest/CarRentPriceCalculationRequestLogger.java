package org.rental.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CarRentPriceCalculationRequestLogger {

    private static final Logger logger = LoggerFactory.getLogger(CarRentPriceCalculationRequestLogger.class);

    void log(CarRentPriceCalculationRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(request);
            logger.info("REQUEST: " + json);
        } catch (JsonProcessingException e) {
            logger.error("Error to convert request to JSON", e);
        }
    }
}

