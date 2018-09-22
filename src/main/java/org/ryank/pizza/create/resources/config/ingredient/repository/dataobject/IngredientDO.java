package org.ryank.pizza.create.resources.config.ingredient.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class IngredientDO {

  @Id
  private String name;

  private double cost;
}
