package houseoflagman.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class SpecialHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private boolean closed;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String description;

    private LocalDate dateTo;
}