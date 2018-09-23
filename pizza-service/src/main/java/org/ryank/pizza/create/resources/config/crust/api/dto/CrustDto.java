package org.ryank.pizza.create.resources.config.crust.api.dto;

import lombok.Data;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;

@Data //for spring
public class CrustDto {

  private String name;
  private double cost;

  public CrustDto(Crust model) {
    this.name = model.getName();
    this.cost = model.getCost();
  }

  public Crust unpack() {
    return new Crust(this.name, this.cost);
  }
}