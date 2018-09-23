package org.ryank.pizza.create.resources.config.cheese.api.dto;

import lombok.Data;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;

@Data //for spring
public class CheeseDto {

  private String name;
  private double cost;

  public CheeseDto(Cheese model) {
    this.name = model.getName();
    this.cost = model.getCost();
  }

  public Cheese unpack() {
    return new Cheese(this.name, this.cost);
  }
}
