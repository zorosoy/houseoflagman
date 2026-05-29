package houseoflagman.service;

import houseoflagman.model.SpecialHours;
import houseoflagman.repository.SpecialHoursRepository;
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
class SpecialHoursServiceTest {

    @Mock
    private SpecialHoursRepository specialHoursRepository;

    @InjectMocks
    private SpecialHoursService specialHoursService;

    @Test
    void isDateClosed_returnsTrue_whenMonday() {
        LocalDate monday = LocalDate.of(2026, 6, 8);
        when(specialHoursRepository.findAll()).thenReturn(List.of());
        assertTrue(specialHoursService.isDateClosed(monday));
    }

    @Test
    void isDateClosed_returnsFalse_whenTuesday() {
        LocalDate tuesday = LocalDate.of(2026, 6, 9);
        when(specialHoursRepository.findAll()).thenReturn(List.of());
        assertFalse(specialHoursService.isDateClosed(tuesday));
    }

    @Test
    void isDateClosed_returnsTrue_whenSpecialHoursClosedOnDate() {
        LocalDate date = LocalDate.of(2026, 12, 25);
        SpecialHours sh = new SpecialHours();
        sh.setDate(date);
        sh.setClosed(true);

        when(specialHoursRepository.findAll()).thenReturn(List.of(sh));
        assertTrue(specialHoursService.isDateClosed(date));
    }

    @Test
    void isDateClosed_returnsFalse_whenSpecialHoursOpenOnDate() {
        LocalDate date = LocalDate.of(2026, 12, 25);
        SpecialHours sh = new SpecialHours();
        sh.setDate(date);
        sh.setClosed(false);
        sh.setOpenTime(LocalTime.of(11, 30));
        sh.setCloseTime(LocalTime.of(20, 0));

        when(specialHoursRepository.findAll()).thenReturn(List.of(sh));
        assertFalse(specialHoursService.isDateClosed(date));
    }

    @Test
    void isDateClosed_returnsTrue_whenDateWithinClosedRange() {
        LocalDate date = LocalDate.of(2026, 12, 25);
        SpecialHours sh = new SpecialHours();
        sh.setDate(LocalDate.of(2026, 12, 24));
        sh.setDateTo(LocalDate.of(2026, 12, 26));
        sh.setClosed(true);

        when(specialHoursRepository.findAll()).thenReturn(List.of(sh));
        assertTrue(specialHoursService.isDateClosed(date));
    }

    @Test
    void getOpenTime_returnsDefault_whenNoSpecialHours() {
        LocalDate date = LocalDate.of(2026, 6, 9);
        when(specialHoursRepository.findAll()).thenReturn(List.of());
        assertEquals(LocalTime.of(11, 30), specialHoursService.getOpenTime(date));
    }
}