package org.rental.rest;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class CarRentPriceCalculationRequestExecutionTimeLogger {

    private static final Logger logger = LoggerFactory.getLogger(CarRentPriceCalculationRequestExecutionTimeLogger.class);

    void logExecutionTime (Stopwatch stopWatch) {
stopWatch.stop();
long elapsedMillis = stopWatch.elapsed().toMillis();
logger.info("Request processing time (ms): " +elapsedMillis);
    }
}
