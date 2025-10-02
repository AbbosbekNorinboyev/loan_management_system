package uz.brb.loan_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.brb.loan_management_system.enums.CardCurrency;
import uz.brb.loan_management_system.enums.CardStatus;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private CardCurrency cardCurrency;
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
}