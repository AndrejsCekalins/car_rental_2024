package org.rental.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarRentPriceCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired private JsonFileReader jsonFileReader;

    @Test
    public void successRequest() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_success.json",
                "rest/CarRentPriceCalculationResponse_success.json"
        );
    }

    @Test
    public void firstNameIsNull() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_firstName_is_null.json",
                "rest/CarRentPriceCalculationResponse_firstName_is_null.json"
        );
    }

    @Test
    public void firstNameIsEmpty() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_firstName_is_empty.json",
                "rest/CarRentPriceCalculationResponse_firstName_is_empty.json"
        );
    }


    @Test
    public void lastNameIsNull() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_lastName_is_null.json",
                "rest/CarRentPriceCalculationResponse_lastName_is_null.json"
        );
    }

    @Test
    public void lastNameIsEmpty() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_lastName_is_empty.json",
                "rest/CarRentPriceCalculationResponse_lastName_is_empty.json"
        );
    }

    @Test
    public void agreementDateFromNotProvided() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_agreementDateFrom_not_provided.json",
                "rest/CarRentPriceCalculationResponse_agreementDateFrom_not_provided.json"
        );
    }

    @Test
    public void agreementDateToNotProvided() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_agreementDateTo_not_provided.json",
                "rest/CarRentPriceCalculationResponse_agreementDateTo_not_provided.json"
        );
    }

    @Test
    public void agreementDateToLessThenAgreementDateFrom() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_dateTo_lessThen_dateFrom.json",
                "rest/CarRentPriceCalculationResponse_dateTo_lessThen_dateFrom.json"
        );
    }

    @Test
    public void agreementDateFromInThePast() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_agreementDateFrom_in_the_past.json",
                "rest/CarRentPriceCalculationResponse_agreementDateFrom_in_the_past.json"
        );
    }

    @Test
    public void agreementDateToInThePast() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_agreementDateTo_in_the_past.json",
                "rest/CarRentPriceCalculationResponse_agreementDateTo_in_the_past.json"
        );
    }

    @Test
    public void selectedVehicleEmpty() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_selectedCar_empty.json",
                "rest/CarRentPriceCalculationResponse_selectedCar_empty.json"
        );
    }

    @Test
    public void selectedVehicleNull() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_selectedCar_null.json",
                "rest/CarRentPriceCalculationResponse_selectedCar_null.json"
        );
    }

    @Test
    public void countryIsEmptyWhenCarPremiumSelected() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_country_empty_car_premium.json",
                "rest/CarRentPriceCalculationResponse_country_empty_car_premium.json"
        );
    }

    @Test
    public void countryIsNullWhenCarPremiumSelected() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_country_null_car_premium.json",
                "rest/CarRentPriceCalculationResponse_country_null_car_premium.json"
        );
    }

    @Test
    public void allFieldsNotProvided() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_allFields_not_provided.json",
                "rest/CarRentPriceCalculationResponse_allFields_not_provided.json"
        );
    }


    private void executeAndCompare(String jsonRequestFilePath,
                                   String jsonResponseFilePath) throws Exception {
        String jsonRequest = jsonFileReader.readJsonFromFile(jsonRequestFilePath);

        MvcResult result = mockMvc.perform(post("/rent/car/")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();

        String jsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);

        assertJson(responseBodyContent)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(jsonResponse);
        ;
    }

}
