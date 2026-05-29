package houseoflagman.service;

import houseoflagman.model.SpecialHours;
import houseoflagman.repository.SpecialHoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialHoursService {

    private final SpecialHoursRepository specialHoursRepository;

    private Optional<SpecialHours> findSpecialHoursForDate(LocalDate date) {
        return specialHoursRepository.findAll().stream()
                .filter(sh -> {
                    LocalDate end = sh.getDateTo() != null ? sh.getDateTo() : sh.getDate();
                    return !date.isBefore(sh.getDate()) && !date.isAfter(end);
                })
                .findFirst();
    }

    public boolean isDateClosed(LocalDate date) {
        if (date == null) return true;
        Optional<SpecialHours> special = findSpecialHoursForDate(date);
        if (special.isPresent()) {
            return special.get().isClosed();
        }
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    public LocalTime getOpenTime(LocalDate date) {
        Optional<SpecialHours> special = findSpecialHoursForDate(date);
        if (special.isPresent() && !special.get().isClosed()) {
            return special.get().getOpenTime();
        }
        return LocalTime.of(11, 30);
    }

    public LocalTime getKitchenCloseTime(LocalDate date) {
        Optional<SpecialHours> special = findSpecialHoursForDate(date);
        if (special.isPresent() && !special.get().isClosed()) {
            return special.get().getCloseTime().minusHours(1);
        }
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return LocalTime.of(21, 0);
        }
        return LocalTime.of(20, 0);
    }
}