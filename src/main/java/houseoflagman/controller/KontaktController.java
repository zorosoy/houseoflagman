package houseoflagman.controller;

import houseoflagman.model.SpecialHours;
import houseoflagman.repository.SpecialHoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class KontaktController {

    private final SpecialHoursRepository specialHoursRepository;

    @GetMapping("/kontakt")
    public String kontakt(Model model) {
        List<SpecialHours> specialHours = specialHoursRepository.findAll();
        specialHours.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        model.addAttribute("specialHours", specialHours);
        return "kontakt";
    }
}