package org.ryank.pizza.create.resources.config.cheese.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;

@RequiredArgsConstructor
@Entity
public class CheeseDO {

  @Id
  private final String name;

  private final double cost;

  public CheeseDO(Cheese cheese) {
    this.name = cheese.getName();
    this.cost = cheese.getCost();
  }

  public Cheese unpack() {
    return new Cheese(name, cost);
  }
}
