package uz.brb.loan_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.brb.loan_management_system.enums.LoanApplicationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal requestedAmount;
    private Integer tenureMonths;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_user_id")
    private AuthUser authUser;

    @Enumerated(EnumType.STRING)
    private LoanApplicationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}