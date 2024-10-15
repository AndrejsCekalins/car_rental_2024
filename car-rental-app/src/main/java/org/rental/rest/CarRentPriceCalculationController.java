package org.rental.rest;

import org.rental.core.CarRentPriceCalculationService;
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
    private CarRentPriceCalculationService calculatePriceService;

    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json")
    public CarRentPriceCalculationResponse calculatePrice(@RequestBody CarRentPriceCalculationRequest request) {
        return calculatePriceService.calculatePrice(request);
    }
}
