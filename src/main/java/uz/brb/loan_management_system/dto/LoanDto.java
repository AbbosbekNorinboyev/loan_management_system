package uz.brb.loan_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoanDto {
    private String accountType;
    private Integer count;
    private Double amount;

    public LoanDto(Long count, Double amount) {
        this.count = count != null ? count.intValue() : 0;
        this.amount = amount != null ? amount : 0.0;
    }
}
