package uz.brb.loan_management_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "api_log")
public class ApiLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_log_id_seq")
    @SequenceGenerator(name = "api_log_id_seq", sequenceName = "api_log_id_seq", allocationSize = 1)
    private Long id;

    private String username;
    private String method;
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime timestamp;
    private Long durationMs;
}