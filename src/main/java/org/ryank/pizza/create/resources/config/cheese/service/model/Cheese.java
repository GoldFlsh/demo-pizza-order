package org.ryank.pizza.create.resources.config.cheese.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Cheese {

  private final String name;
  private final double cost;

  public String getName() {
    return name.toUpperCase();
  }
}
