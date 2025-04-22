package jru_apl.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
