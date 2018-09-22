package org.ryank.pizza.create.resources.config.ingredient.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;

public interface IngredientService {

  /**
   * Return all supported Ingredients
   * @return
   */
  List<Ingredient> get();

  /**
   * Get a specific Ingredient by name
   * @param name
   * @return Ingredient model
   */
  Optional<Ingredient> get(String name);

  /**
   * Create a new Ingredient given a new Ingredient model.
   *
   * Note must be uniquely named
   * @param Ingredient
   * @return The newly created Ingredient
   */
  Ingredient create(Ingredient Ingredient);

  /**
   * Updates the Ingredient given a model with
   * a matching Ingredient name
   * @param Ingredient
   * @return
   */
  Ingredient update(Ingredient Ingredient);

  /**
   * Deletes a Ingredient given the Ingredient's name
   * @param name
   * @return the deleted Ingredient
   */
  Ingredient delete(String name);
}
