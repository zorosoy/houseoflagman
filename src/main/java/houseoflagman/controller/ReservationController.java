package houseoflagman.controller;

import houseoflagman.model.Reservation;
import houseoflagman.model.SpecialHours;
import houseoflagman.repository.SpecialHoursRepository;
import houseoflagman.service.MailService;
import houseoflagman.service.ReservationService;
import houseoflagman.service.SpecialHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/reservieren")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final SpecialHoursService specialHoursService;
    private final SpecialHoursRepository specialHoursRepository;
    private final MailService mailService;

    private void addSpecialHours(Model model) {
        List<SpecialHours> specialHours = specialHoursRepository.findAll();
        specialHours.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        model.addAttribute("specialHours", specialHours);
    }

    @GetMapping
    public String reservieren(Model model) {
        model.addAttribute("reservation", new Reservation());
        addSpecialHours(model);
        return "reservieren";
    }

    @GetMapping("/slots")
    @ResponseBody
    public Map<String, Object> getSlots(@RequestParam String date,
                                        @RequestParam(defaultValue = "1") int persons) {
        LocalDate localDate = LocalDate.parse(date);
        Map<String, Object> response = new HashMap<>();

        if (specialHoursService.isDateClosed(localDate)) {
            response.put("closed", true);
            return response;
        }

        LocalTime open = specialHoursService.getOpenTime(localDate);
        LocalTime kitchenClose = specialHoursService.getKitchenCloseTime(localDate);

        List<Map<String, Object>> slots = new ArrayList<>();
        LocalTime current = open;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        while (!current.isAfter(kitchenClose)) {
            Map<String, Object> slot = new HashMap<>();
            slot.put("time", current.format(fmt));
            int booked = reservationService.getBookedPersons(localDate, current);
            int remaining = 20 - booked;
            boolean available = remaining >= persons;
            slot.put("booked", booked);
            slot.put("available", available);
            slot.put("remaining", remaining);
            slots.add(slot);
            current = current.plusMinutes(15);
        }

        response.put("closed", false);
        response.put("slots", slots);
        return response;
    }

    @PostMapping
    public String submitReservation(@Valid @ModelAttribute Reservation reservation,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            addSpecialHours(model);
            return "reservieren";
        }

        if (specialHoursService.isDateClosed(reservation.getDate())) {
            model.addAttribute("capacityError", "An diesem Tag ist das Restaurant geschlossen.");
            model.addAttribute("reservation", reservation);
            addSpecialHours(model);
            return "reservieren";
        }

        if (!reservationService.isCapacityAvailable(
                reservation.getDate(),
                reservation.getTime(),
                reservation.getPersons())) {
            model.addAttribute("capacityError",
                    "Für diesen Zeitraum sind leider keine Plätze mehr verfügbar. Bitte wähle eine andere Zeit.");
            model.addAttribute("reservation", reservation);
            addSpecialHours(model);
            return "reservieren";
        }

        reservationService.save(reservation);

        try {
            mailService.sendReservationConfirmation(reservation);
        } catch (Exception e) {
            System.err.println("Mail konnte nicht gesendet werden: " + e.getMessage());
        }

        return "redirect:/reservieren?success&date=" + reservation.getDate() + "&time=" + reservation.getTime();    }
}