package uz.brb.loan_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CardControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createCardIntegrationTest() throws Exception {
        String requestBody = """
                {
                  "cardNumber": "8600123412341234",
                  "cardHolder": "Norinboyev Abbos",
                  "expiryDate": "09/27",
                  "balance": 100000,
                  "cardStatus": "ACTIVE",
                  "cardCurrency": "UZS"
                }
                """;

        mockMvc.perform(post("/api/cards/create")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Card successfully created"))
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    void getCardIntegrationTest() throws Exception {
        mockMvc.perform(get("/api/cards/get")
                        .param("id", "1")
                        .contentType("application/json"))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Card successfully found"))
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    void getCardNotFoundIntegrationTest() throws Exception {
        mockMvc.perform(get("/api/cards/get")
                        .param("id", "5112")
                        .contentType("application/json"))
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("message").value("Card not found: 5112"))
                .andExpect(jsonPath("success").value(false));
    }

    @Test
    void getAllCardIntegrationTest() throws Exception {
        mockMvc.perform(get("/api/cards/getAll")
                        .contentType("application/json"))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Card list successfully found"))
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    void updateCardIntegrationTest() throws Exception {
        String requestBody = """
                {
                  "cardNumber": "8600123412341234",
                  "cardHolder": "Norinboyev Abbos",
                  "expiryDate": "09/27",
                  "balance": 200000,
                  "cardStatus": "ACTIVE",
                  "cardCurrency": "UZS"
                }
                """;

        mockMvc.perform(put("/api/cards/update")
                        .param("id", "1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Card successfully updated"))
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    void deleteCardIntegrationTest() throws Exception {
        mockMvc.perform(delete("/api/cards/delete")
                        .param("id", "1")
                        .contentType("application/json"))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Card successfully deleted"))
                .andExpect(jsonPath("success").value(true));
    }
}
