package org.ryank.pizza.create.resources.config.cheese.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cheese {

  @Id
  private String name;

  private double cost;
}
