package uz.brb.loan_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.brb.loan_management_system.enums.LoanStatus;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private LoanApplication application;

    private BigDecimal principal;

    private BigDecimal interestRate;

    private Integer tenureMonths;

    private BigDecimal outstandingAmount;

    @Enumerated(EnumType.STRING)
    private LoanStatus status; // ACTIVE, CLOSED, DEFAULTED
}
