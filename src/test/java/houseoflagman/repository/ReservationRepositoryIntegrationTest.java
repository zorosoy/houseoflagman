package houseoflagman.repository;

import houseoflagman.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryIntegrationTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void findByDate_returnsReservationsForDate() {
        Reservation r = new Reservation();
        r.setName("Test User");
        r.setEmail("test@test.ch");
        r.setPhone("+41791234567");
        r.setPersons(2);
        r.setDate(LocalDate.of(2026, 6, 10));
        r.setTime(LocalTime.of(12, 0));
        reservationRepository.save(r);

        List<Reservation> result = reservationRepository.findByDate(LocalDate.of(2026, 6, 10));
        assertEquals(1, result.size());
        assertEquals("Test User", result.getFirst().getName());
    }

    @Test
    void findByDate_returnsEmpty_whenNoReservationsForDate() {
        List<Reservation> result = reservationRepository.findByDate(LocalDate.of(2099, 1, 1));
        assertTrue(result.isEmpty());
    }
}