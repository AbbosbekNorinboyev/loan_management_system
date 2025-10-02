package uz.brb.loan_management_system.dto.request;

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
public class CardRequest {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private BigDecimal balance;
    private CardCurrency cardCurrency;
    private CardStatus cardStatus;
}