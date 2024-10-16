package org.rental.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarRentPriceCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired private JsonFileReader jsonFileReader;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void successRequest() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_success.json",
                "rest/CarRentPriceCalculationResponse_success.json"
        );
    }

    @Test
    public void firstNameNotProvided() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_firstName_not_provided.json",
                "rest/CarRentPriceCalculationResponse_firstName_not_provided.json"
        );
    }

    @Test
    public void lastNameNotProvided() throws Exception {
        executeAndCompare(
                "rest/CarRentPriceCalculationRequest_lastName_not_provided.json",
                "rest/CarRentPriceCalculationResponse_lastName_not_provided.json"
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

        assertEquals(mapper.readTree(responseBodyContent), mapper.readTree(jsonResponse));
    }

}
