package houseoflagman.service;

import houseoflagman.model.MenuCategory;
import houseoflagman.model.MenuItem;
import houseoflagman.repository.MenuCategoryRepository;
import houseoflagman.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository itemRepository;

    public List<MenuCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public MenuCategory getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public List<MenuItem> getItemsByCategory(Long categoryId) {
        return itemRepository.findByCategory_Id(categoryId);
    }
}