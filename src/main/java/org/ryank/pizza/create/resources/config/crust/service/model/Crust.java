package org.ryank.pizza.create.resources.config.crust.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Crust {

  private final String name;
  private final double cost;

}
