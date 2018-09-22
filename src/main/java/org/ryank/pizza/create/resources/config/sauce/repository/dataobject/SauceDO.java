package org.ryank.pizza.create.resources.config.sauce.repository.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SauceDO {

  @Id
  private String name;

  private double cost;
}
