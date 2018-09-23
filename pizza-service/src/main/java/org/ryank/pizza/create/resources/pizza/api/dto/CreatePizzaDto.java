package org.ryank.pizza.create.resources.pizza.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //for spring
@AllArgsConstructor
public class CreatePizzaDto {

  private String cheese;
  private String crust;
  private String sauce;
  private List<String> ingredients;

}