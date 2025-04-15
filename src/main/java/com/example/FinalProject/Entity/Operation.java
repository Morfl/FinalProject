package com.example.FinalProject.Entity;

import com.example.FinalProject.Enum.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Operation {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    private Client client;

    @Column(nullable = false)
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OperationType operationType;

    @NonNull
    @Positive
    @Column(nullable = false)
    BigDecimal amount;

    @Override
    public String toString() {
        return "OPERATION " +
                '{' + "ДАТА='" + date + '\'' +
                ", ТИП=" + operationType.getDescription() +
                ", СУММА=" + amount +
                '}';
    }
}
