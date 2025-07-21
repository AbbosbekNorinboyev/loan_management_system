package uz.pdp.loan_management_system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@JsonSerialize
public class Empty implements Serializable {
}