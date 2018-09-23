package org.ryank.pizza.create.resources.pizza.api.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.ryank.pizza.create.resources.config.cheese.api.dto.CheeseDto;
import org.ryank.pizza.create.resources.config.crust.api.dto.CrustDto;
import org.ryank.pizza.create.resources.config.ingredient.api.dto.IngredientDto;
import org.ryank.pizza.create.resources.config.sauce.api.dto.SauceDto;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;

@Data
public class PizzaDto {

  private Integer id;
  private CheeseDto cheese;
  private CrustDto crust;
  private SauceDto sauce;
  private List<IngredientDto> ingredients;

  public PizzaDto(Pizza pizza) {
    this.id = pizza.getId();
    this.cheese = new CheeseDto(pizza.getCheese());
    this.crust = new CrustDto(pizza.getCrust());
    this.sauce = new SauceDto(pizza.getSauce());
    this.ingredients = pizza.getIngredients().stream()
        .map(IngredientDto::new)
        .collect(Collectors.toList());
  }
}