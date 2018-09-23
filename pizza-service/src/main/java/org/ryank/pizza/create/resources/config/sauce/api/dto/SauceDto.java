package org.ryank.pizza.create.resources.config.sauce.api.dto;

import lombok.Data;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;

@Data //for spring
public class SauceDto {

  private String name;
  private double cost;

  public SauceDto(Sauce model) {
    this.name = model.getName();
    this.cost = model.getCost();
  }

  public Sauce unpack() {
    return new Sauce(this.name, this.cost);
  }
}