package org.ryank.pizza.create.resources.config.crust.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;

@Data
@Entity
public class CrustDO {

  @Id
  private String name;

  private double cost;

  public CrustDO(Crust crust) {
    this.name = crust.getName();
    this.cost = crust.getCost();
  }

  public Crust unpack() {
    return new Crust(name, cost);
  }
}
