package houseoflagman.controller;

import houseoflagman.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public String menu(Model model, @RequestParam(required = false) Long categoryId) {
        var categories = menuService.getAllCategories();
        model.addAttribute("categories", categories);
        if (categoryId != null) {
            model.addAttribute("selectedCategory", menuService.getCategoryById(categoryId));
            model.addAttribute("items", menuService.getItemsByCategory(categoryId));
        } else if (!categories.isEmpty()) {
            model.addAttribute("selectedCategory", categories.get(0));
            model.addAttribute("items", menuService.getItemsByCategory(categories.get(0).getId()));
        }
        return "menu";
    }
}