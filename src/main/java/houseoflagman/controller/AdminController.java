package houseoflagman.controller;

import houseoflagman.model.Reservation;
import houseoflagman.model.SpecialHours;
import houseoflagman.repository.ReservationRepository;
import houseoflagman.repository.SpecialHoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ReservationRepository reservationRepository;
    private final SpecialHoursRepository specialHoursRepository;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping
    public String dashboard(Model model, @RequestParam(defaultValue = "today") String tab) {

        LocalDate today = LocalDate.now();

        // Abgelaufene Sonderöffnungszeiten automatisch löschen
        specialHoursRepository.findAll().stream()
                .filter(sh -> {
                    LocalDate endDate = sh.getDateTo() != null ? sh.getDateTo() : sh.getDate();
                    return endDate.isBefore(today);
                })
                .forEach(sh -> specialHoursRepository.delete(sh));

        // Reservationen laden und sortieren
        List<Reservation> all = reservationRepository.findAll();
        all.sort((a, b) -> {
            int dateComp = a.getDate().compareTo(b.getDate());
            if (dateComp != 0) return dateComp;
            return a.getTime().compareTo(b.getTime());
        });

        List<Reservation> todayRes = all.stream()
                .filter(r -> r.getDate().isEqual(today))
                .collect(Collectors.toList());

        List<Reservation> upcoming = all.stream()
                .filter(r -> r.getDate().isAfter(today))
                .collect(Collectors.toList());

        List<Reservation> past = all.stream()
                .filter(r -> r.getDate().isBefore(today))
                .collect(Collectors.toList());

        // Sonderöffnungszeiten sortiert nach Datum
        List<SpecialHours> specialHours = specialHoursRepository.findAll();
        specialHours.sort((a, b) -> a.getDate().compareTo(b.getDate()));

        model.addAttribute("todayReservations", todayRes);
        model.addAttribute("reservations", upcoming);
        model.addAttribute("pastReservations", past);
        model.addAttribute("specialHours", specialHours);
        model.addAttribute("activeTab", tab);
        return "admin/dashboard";
    }

    @PostMapping("/reservations/{id}/delete")
    public String deleteReservation(@PathVariable Long id,
                                    @RequestParam(defaultValue = "today") String tab) {
        reservationRepository.deleteById(id);
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/reservations/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam Reservation.ReservationStatus status,
                               @RequestParam(defaultValue = "today") String tab) {
        reservationRepository.findById(id).ifPresent(r -> {
            r.setStatus(status);
            reservationRepository.save(r);
        });
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/special-hours/add")
    public String addSpecialHours(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam boolean closed,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime openTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime closeTime,
            @RequestParam String description) {

        SpecialHours sh = new SpecialHours();
        sh.setDate(date);
        sh.setDateTo(dateTo);
        sh.setClosed(closed);
        sh.setOpenTime(openTime);
        sh.setCloseTime(closeTime);
        sh.setDescription(description);
        specialHoursRepository.save(sh);
        return "redirect:/admin";
    }

    @PostMapping("/special-hours/{id}/delete")
    public String deleteSpecialHours(@PathVariable Long id) {
        specialHoursRepository.deleteById(id);
        return "redirect:/admin";
    }
}