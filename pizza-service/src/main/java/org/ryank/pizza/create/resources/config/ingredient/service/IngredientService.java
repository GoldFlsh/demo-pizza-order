package org.ryank.pizza.create.resources.config.ingredient.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;

public interface IngredientService {

  /**
   * @return - all configured ingredients
   */
  List<Ingredient> get();

  /**
   * @param name of ingredient to query for
   * @return found ingredient
   */
  Optional<Ingredient> get(String name);

  /**
   * Create a new ingredient given a new ingredient model.
   *
   * Note must be uniquely named
   * @param ingredient - new ingredient
   * @return The newly created ingredient
   */
  Ingredient create(Ingredient ingredient);

  /**
   * Updates the Ingredient given a model with
   * a matching Ingredient name
   * @param ingredient - updated ingredient
   * @return - updated ingredient
   */
  Ingredient update(Ingredient ingredient);

  /**
   * Deletes a Ingredient given the Ingredient's name
   * @param name - of ingredient to delete
   */
  void delete(String name);
}
