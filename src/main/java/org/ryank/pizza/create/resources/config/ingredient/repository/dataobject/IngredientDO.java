package org.ryank.pizza.create.resources.config.ingredient.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;

@Entity
public class IngredientDO {

  @Id
  private String name;

  private double cost;

  public IngredientDO(Ingredient cheese) {
    this.name = cheese.getName();
    this.cost = cheese.getCost();
  }

  public Ingredient unpack() {
    return new Ingredient(name, cost);
  }
}
