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

  //TODO: relies on ext. service for cleanup of created pizzas consider a timeout
  //TODO: Requires ext. service to keep track of pizza id...maybe that's ok in this case
  //TODO: Duplicate pizzas across many 'users' and many orders will end up in DB...consider possible solutions
  //may be ok if pizzas are always cleaned up after orders are finished. But if pizzas are 'saved' for reuse then it would become a problem.
}
