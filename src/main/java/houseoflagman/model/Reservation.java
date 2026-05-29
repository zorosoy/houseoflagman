package houseoflagman.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email(message = "Bitte eine gültige E-Mail-Adresse eingeben")
    private String email;

    @NotBlank(message = "Telefonnummer ist erforderlich")
    @Pattern(regexp = "^[+0][0-9 ]{6,17}$", message = "Bitte eine gültige Telefonnummer eingeben")
    private String phone;

    @Min(1)
    @Max(8)
    private int persons;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    public enum ReservationStatus {
        PENDING, ARRIVED, NO_SHOW
    }
}