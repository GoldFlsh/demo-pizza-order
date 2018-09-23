package org.ryank.pizza.create.resources.pizza.service.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Pizza {

  private Integer id;
  private final Cheese cheese;
  private final Crust crust;
  private final Sauce sauce;
  private final List<Ingredient> ingredients;
}