package org.ryank.pizza.create.resources.config.sauce.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Sauce {

  private final String name;
  private final double cost;

  public String getName() {
    return name.toUpperCase();
  }
}
