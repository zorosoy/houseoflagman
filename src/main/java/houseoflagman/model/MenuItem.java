package houseoflagman.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean recommended;
    private boolean hasVegetarian;
    private boolean hasChicken;
    private boolean hasBeef;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MenuCategory category;
}