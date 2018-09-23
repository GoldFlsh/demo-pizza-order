package org.ryank.pizza.create.resources.config.ingredient.api.dto;

import lombok.Data;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;

@Data //for spring
public class IngredientDto {

  private String name;
  private double cost;

  public IngredientDto(Ingredient model) {
    this.name = model.getName();
    this.cost = model.getCost();
  }

  public Ingredient unpack() {
    return new Ingredient(this.name, this.cost);
  }
}