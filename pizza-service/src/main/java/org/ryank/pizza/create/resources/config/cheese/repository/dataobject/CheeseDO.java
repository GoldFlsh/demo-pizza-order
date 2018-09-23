package org.ryank.pizza.create.resources.config.cheese.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;

@Setter
@NoArgsConstructor //for spring
@Entity(name = "CHEESE")
public class CheeseDO {

  @Id
  private String name;

  private double cost;

  public CheeseDO(Cheese cheese) {
    this.name = cheese.getName();
    this.cost = cheese.getCost();
  }

  public Cheese unpack() {
    return new Cheese(name, cost);
  }
}
