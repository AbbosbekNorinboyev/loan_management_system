package uz.brb.loan_management_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uz.brb.loan_management_system.config.JWTAuthenticationFilter;
import uz.brb.loan_management_system.controller.CardController;
import uz.brb.loan_management_system.dto.request.CardRequest;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.enums.CardCurrency;
import uz.brb.loan_management_system.enums.CardStatus;
import uz.brb.loan_management_system.service.CardService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CardController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTAuthenticationFilter.class) // 🔹 JWTFilter ni exclude qilyapmiz
        })   // 🔹 faqat Controller test
@AutoConfigureMockMvc(addFilters = false)  // 🔥 security filterlarni o‘chiradi
class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // JSON serialize/deserialize uchun

    // 🔹 Controller ichida ishlatiladigan service qatlamini mock qilamiz
    @MockitoBean
    private CardService cardService;

    @Test
    void createCardTest() throws Exception {
        CardRequest cardRequest = CardRequest.builder()
                .cardNumber("8600123412341234")
                .cardHolder("Norinboyev Abbos")
                .expiryDate("09/27")
                .balance(BigDecimal.valueOf(200000))
                .cardStatus(CardStatus.ACTIVE)
                .cardCurrency(CardCurrency.USD)
                .build();

        // service mock javobi (controller ichida ishlatiladi)
        when(cardService.createCard(any()))
                .thenReturn(new Response("Card successfully created", true, HttpStatus.OK.value(), HttpStatus.OK));

        mockMvc.perform(post("/api/cards/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(cardRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Card successfully created"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getCardTest() throws Exception {
        when(cardService.getCard(7L))
                .thenReturn(new Response("Card successfully found", true, HttpStatus.OK.value(), HttpStatus.OK));

        mockMvc.perform(get("/api/cards/get")
                        .param("id", "7")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Card successfully found"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getCardNotFoundTest() throws Exception {
        when(cardService.getCard(5112L))
                .thenReturn(new Response("Card not found: 5112", false, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/cards/get")
                        .param("id", "5112")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Card not found: 5112"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void getAllCardsTest() throws Exception {
        when(cardService.getAllCard())
                .thenReturn(new Response("Card list successfully found", true, HttpStatus.OK.value(), HttpStatus.OK));

        mockMvc.perform(get("/api/cards/getAll")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Card list successfully found"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void updateCardTest() throws Exception {
        CardRequest cardRequest = CardRequest.builder()
                .cardNumber("8600123412341234")
                .cardHolder("Norinboyev Abbos")
                .expiryDate("09/27")
                .balance(BigDecimal.valueOf(200000))
                .cardStatus(CardStatus.ACTIVE)
                .cardCurrency(CardCurrency.USD)
                .build();

        Response response = new Response(
                "Card successfully updated",
                true,
                HttpStatus.OK.value(),
                HttpStatus.OK
        );

        when(cardService.updateCard(eq(cardRequest), eq(7L))).thenReturn(response);

        mockMvc.perform(put("/api/cards/update")
                        .param("id", "7")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(cardRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Card successfully updated"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void deleteCardTest() throws Exception {
        when(cardService.deleteCard(6L))
                .thenReturn(new Response("Card successfully deleted", true, HttpStatus.OK.value(), HttpStatus.OK));

        mockMvc.perform(delete("/api/cards/delete")
                        .param("id", "6")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Card successfully deleted"))
                .andExpect(jsonPath("$.success").value(true));
    }
}
