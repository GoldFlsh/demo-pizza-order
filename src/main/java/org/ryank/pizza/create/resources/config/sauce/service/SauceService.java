package org.ryank.pizza.create.resources.config.sauce.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;

public interface SauceService {

  /**
   * Return all supported Sauces
   * @return
   */
  List<Sauce> get();

  /**
   * Get a specific Sauce by name
   * @param name
   * @return Sauce model
   */
  Optional<Sauce> get(String name);

  /**
   * Create a new Sauce given a new Sauce model.
   *
   * Note must be uniquely named
   * @param Sauce
   * @return The newly created Sauce
   */
  Sauce create(Sauce Sauce);

  /**
   * Updates the Sauce given a model with
   * a matching Sauce name
   * @param Sauce
   * @return
   */
  Sauce update(Sauce Sauce);

  /**
   * Deletes a Sauce given the Sauce's name
   * @param name
   * @return the deleted Sauce
   */
  Sauce delete(String name);
}
