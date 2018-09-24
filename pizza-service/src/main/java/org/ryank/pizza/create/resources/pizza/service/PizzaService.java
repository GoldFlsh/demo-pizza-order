package org.ryank.pizza.create.resources.pizza.service;

import java.util.Optional;
import org.ryank.pizza.create.resources.pizza.api.dto.CreatePizzaDto;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;

public interface PizzaService {

  /**
   * Get a Pizza by the identifier generated at
   * creation time
   * @param id - id of the pizza
   * @return the pizza found
   */
  Optional<Pizza> get(Integer id);

  /**
   * Persists a new pizza to the database
   * @param newPizza - a new pizza to create
   * @return - the created pizza
   */
  Pizza create(CreatePizzaDto newPizza);

  /**
   * Delete a particular pizza
   * @param id - id of pizza to delete
   */
  void delete(Integer id);

}
