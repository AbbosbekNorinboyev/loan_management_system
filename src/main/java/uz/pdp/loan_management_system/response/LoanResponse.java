package uz.pdp.loan_management_system.response;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanResponse {
    private String loanName;
    private Double loanAmount;
    private String status;
    private Long authUserId;
}