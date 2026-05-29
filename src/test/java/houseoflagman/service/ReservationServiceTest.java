package houseoflagman.service;

import houseoflagman.model.Reservation;
import houseoflagman.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private LocalDate date;
    private LocalTime time;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2026, 6, 10);
        time = LocalTime.of(12, 0);
    }

    @Test
    void getBookedPersons_returnsZero_whenNoReservations() {
        when(reservationRepository.findByDate(date)).thenReturn(List.of());
        int result = reservationService.getBookedPersons(date, time);
        assertEquals(0, result);
    }

    @Test
    void getBookedPersons_returnsCorrectSum_whenReservationsExist() {
        Reservation r1 = new Reservation();
        r1.setPersons(3);
        r1.setDate(date);
        r1.setTime(LocalTime.of(11, 30));

        Reservation r2 = new Reservation();
        r2.setPersons(4);
        r2.setDate(date);
        r2.setTime(LocalTime.of(12, 0));

        when(reservationRepository.findByDate(date)).thenReturn(List.of(r1, r2));
        int result = reservationService.getBookedPersons(date, time);
        assertEquals(7, result);
    }

    @Test
    void isCapacityAvailable_returnsTrue_whenEnoughSpace() {
        when(reservationRepository.findByDate(date)).thenReturn(List.of());
        assertTrue(reservationService.isCapacityAvailable(date, time, 5));
    }

    @Test
    void isCapacityAvailable_returnsFalse_whenFull() {
        Reservation r = new Reservation();
        r.setPersons(18);
        r.setDate(date);
        r.setTime(time);

        when(reservationRepository.findByDate(date)).thenReturn(List.of(r));
        assertFalse(reservationService.isCapacityAvailable(date, time, 5));
    }
}