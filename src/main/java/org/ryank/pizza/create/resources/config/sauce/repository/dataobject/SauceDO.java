package org.ryank.pizza.create.resources.config.sauce.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;

@Data
@Entity(name = "CRUST")
public class SauceDO {

  @Id
  private String name;

  private double cost;

  public SauceDO(Sauce model) {
    this.name = model.getName();
    this.cost = model.getCost();
  }

  public Sauce unpack() {
    return new Sauce(name, cost);
  }
}
