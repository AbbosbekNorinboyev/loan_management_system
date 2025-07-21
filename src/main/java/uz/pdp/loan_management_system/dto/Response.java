package uz.pdp.loan_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"success","data","error"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T,V> implements Serializable {
    @JsonProperty("success")
    @Schema(description = "status", type = "boolean", defaultValue = "false")
    private boolean success;
    @JsonProperty("data")
    private T data;
    @JsonProperty("error")
    private V error;
}