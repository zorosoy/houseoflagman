package houseoflagman;

import houseoflagman.model.MenuCategory;
import houseoflagman.model.MenuItem;
import houseoflagman.repository.MenuCategoryRepository;
import houseoflagman.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository itemRepository;

    @Override
    public void run(String... args) {

        MenuCategory lagman = createCategory("Lagman", "Handgezogene Nudeln – täglich frisch zubereitet.");
        MenuCategory reis = createCategory("Reisgerichte", "Traditionelle Reisgerichte nach uigurischer Art.");
        MenuCategory suppen = createCategory("Suppen", "Kräftige Suppen – wärmend und sättigend.");
        MenuCategory manti = createCategory("Manti", "Handgemachte Teigtaschen nach uigurischer Tradition.");

        categoryRepository.save(lagman);
        categoryRepository.save(reis);
        categoryRepository.save(suppen);
        categoryRepository.save(manti);

        // Lagman
        saveItem("Somen",
                "Wokgebraten mit Rindfleisch, Knoblauch, Paprika, Sesam und Chilis.",
                25, lagman, true, "/images/lagman1.jpg", true, true, true);

        saveItem("Lagman Royal",
                "In aromatischer Sauce mit Rindfleisch, Zwiebeln, Lauch, Tomaten und Paprika.",
                25, lagman, true, "/images/lagman2.jpg", true, false, true);

        saveItem("Lagman Hackfleisch",
                "Mit kräftiger Hackfleischsauce aus Rindfleisch, Tomaten, Paprika und frischem Saisongemüse.",
                25, lagman, false, "/images/lagman3.jpg", true, false, true);

        saveItem("Lagman Nudelperlen",
                "Handgezogene Nudelstücke, goldbraun gebraten in würziger Tomatensauce mit Rindfleisch und Paprika.",
                25, lagman, false, "/images/lagman4.jpg", false, false, true);

        saveItem("Lagman Breit",
                "Breite, handgezogene Nudeln mit zartem Poulet, Kartoffeln, Paprika, Ingwer und Knoblauch. Leicht scharf.",
                27, lagman, false, "/images/lagman5.jpg", false, true, false);


        // Reisgerichte
        saveItem("Wok mit Reis",
                "Zartes Rindfleisch mit frischem Gemüse, serviert mit locker gekochtem Reis.",
                23, reis, false, "/images/reis1.jpg", true, true, true);

        saveItem("Uigurisches Plov",
                "Traditionelles Reisgericht mit saftigem Rindfleisch, Karotten und Zwiebeln, langsam gegart.",
                23, reis, true, "/images/reis2.jpg", false, false, true);

        // Suppen
        saveItem("Nudelsuppe",
                "Kräftige, sättigende Nudelsuppe mit zartem Rindfleisch.",
                21, suppen, false, "/images/suppe1.jpg", false, false, true);

        saveItem("Manti in Suppe",
                "Aromatische Suppe mit handgemachten Teigtaschen, gefüllt mit Rinderhackfleisch und Zwiebeln.",
                25, suppen, true, "/images/suppe2.jpg", false, false, true);

        // Manti
        saveItem("Uigurische Manti",
                "Handgemachte Teigtaschen mit gewürztem Rinderhackfleisch und Zwiebeln, serviert mit hausgemachter Chilisauce.",
                25, manti, false, "/images/manti1.jpg", false, false, true);

        saveItem("Uigurische Teigtaschen",
                "Gekochte Teigtaschen mit saftigem Rindfleisch und Zwiebeln, nach uigurischer Art gewürzt. Mit hausgemachter Chilisauce.",
                25, manti, false, "/images/manti2.jpg", false, false, true);
    }

    private MenuCategory createCategory(String name, String description) {
        MenuCategory cat = new MenuCategory();
        cat.setName(name);
        cat.setDescription(description);
        return cat;
    }

    private void saveItem(String name, String description, double price,
                          MenuCategory category, boolean recommended, String imageUrl,
                          boolean hasVegetarian, boolean hasChicken, boolean hasBeef) {
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setRecommended(recommended);
        item.setImageUrl(imageUrl);
        item.setHasVegetarian(hasVegetarian);
        item.setHasChicken(hasChicken);
        item.setHasBeef(hasBeef);
        itemRepository.save(item);
    }
}