package org.rental.rest;

import com.google.common.base.Stopwatch;
import org.rental.core.services.CarRentPriceCalculationService;
import org.rental.dto.CarRentPriceCalculationRequest;
import org.rental.dto.CarRentPriceCalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rent/car")
public class CarRentPriceCalculationController {

    @Autowired
    private CarRentPriceCalculationRequestLogger requestLogger;
    @Autowired
    private CarRentPriceCalculationResponseLogger responseLogger;
    @Autowired
    private  CarRentPriceCalculationRequestExecutionTimeLogger executionTimeLogger;
    @Autowired
    private CarRentPriceCalculationService calculatePriceService;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public CarRentPriceCalculationResponse calculatePrice(@RequestBody CarRentPriceCalculationRequest request) {
        Stopwatch stopwatch =Stopwatch.createStarted();
        CarRentPriceCalculationResponse response = processRequest(request);
        executionTimeLogger.logExecutionTime(stopwatch);
        return response;
    }

    private CarRentPriceCalculationResponse processRequest (CarRentPriceCalculationRequest request) {
        requestLogger.log(request);
        CarRentPriceCalculationResponse response =calculatePriceService.calculatePrice(request);
        responseLogger.log(response);
        return response;
    }
}
