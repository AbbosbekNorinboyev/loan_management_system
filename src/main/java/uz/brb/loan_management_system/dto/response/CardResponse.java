package uz.brb.loan_management_system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.brb.loan_management_system.enums.CardCurrency;
import uz.brb.loan_management_system.enums.CardStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponse {
    private Long id;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private BigDecimal balance;
    private CardCurrency cardCurrency;
    private CardStatus cardStatus;
}