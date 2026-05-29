package houseoflagman.service;

import houseoflagman.model.Reservation;
import houseoflagman.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public int getBookedPersons(LocalDate date, LocalTime slotTime) {
        List<Reservation> all = reservationRepository.findByDate(date);

        return all.stream()
                .filter(r -> {
                    return !r.getTime().isAfter(slotTime)
                            && slotTime.isBefore(r.getTime().plusHours(2));
                })
                .mapToInt(Reservation::getPersons)
                .sum();
    }

    public boolean isCapacityAvailable(LocalDate date, LocalTime time, int persons) {
        return (getBookedPersons(date, time) + persons) <= 20;
    }
}