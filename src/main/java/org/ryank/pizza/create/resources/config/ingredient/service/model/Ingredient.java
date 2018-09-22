package org.ryank.pizza.create.resources.config.ingredient.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Ingredient {

  private final String name;
  private final double cost;

  public String getName() {
    return name.toUpperCase();
  }
}
