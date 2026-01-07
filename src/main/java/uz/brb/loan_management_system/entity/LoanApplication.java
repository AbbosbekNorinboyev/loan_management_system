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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_application_id_seq")
    @SequenceGenerator(name = "loan_application_id_seq", sequenceName = "loan_application_id_seq", allocationSize = 1)
    private Long id;

    private BigDecimal amount;
    private Integer tenureMonths;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_user_id")
    private AuthUser authUser;

    @Enumerated(EnumType.STRING)
    private LoanApplicationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}