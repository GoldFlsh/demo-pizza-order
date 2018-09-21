package org.ryank.demopizzaorder.resources.pizza.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Sauce {

  @Id
  private String name;

  private double cost;
}
