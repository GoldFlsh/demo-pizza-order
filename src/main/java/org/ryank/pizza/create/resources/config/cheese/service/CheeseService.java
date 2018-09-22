package org.ryank.pizza.create.resources.config.cheese.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;

public interface CheeseService {

  /**
   * Return all supported cheeses
   * @return
   */
  List<Cheese> get();

  /**
   * Get a specific cheese by name
   * @param name
   * @return cheese model
   */
  Optional<Cheese> get(String name);

  /**
   * Create a new cheese given a new cheese model.
   *
   * Note must be uniquely named
   * @param cheese
   * @return The newly created cheese
   */
  Cheese create(Cheese cheese);

  /**
   * Updates the cheese given a model with
   * a matching cheese name
   * @param cheese
   * @return
   */
  Cheese update(Cheese cheese);

  /**
   * Deletes a cheese given the cheese's name
   * @param name
   * @return the deleted cheese
   */
  Cheese delete(String name);
}
