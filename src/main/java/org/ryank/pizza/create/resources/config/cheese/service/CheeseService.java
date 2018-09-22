package org.ryank.pizza.create.resources.config.cheese.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;

public interface CheeseService {

  /**
   * Return all configured cheeses
   * @return list of all configured cheeses
   */
  List<Cheese> get();

  /**
   * Get a specific cheese by name
   * @param name - name of the cheese to query for
   * @return model of the cheese found
   */
  Optional<Cheese> get(String name);

  /**
   * Create a new cheese given a new cheese model.
   *
   * Note must be uniquely named
   * @param cheese - the model of the cheese to add
   * @return The newly created cheese
   */
  Cheese create(Cheese cheese);

  /**
   * Updates the cheese given a model with
   * a matching cheese name
   * @param cheese - the model of the cheese to update with associated changes
   * @return - the cheese after updates
   */
  Cheese update(Cheese cheese);

  /**
   * Deletes a cheese given the cheese's name
   * @param name - name of the cheese to delete
   */
  void delete(String name);
}
