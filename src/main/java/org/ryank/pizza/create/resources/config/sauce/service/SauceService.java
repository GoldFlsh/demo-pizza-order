package org.ryank.pizza.create.resources.config.sauce.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;

public interface SauceService {

  /**
   * @return all configured sauces
   */
  List<Sauce> get();

  /**
   * Get a specific Sauce by name
   * @param name - name of sauce to find
   * @return Sauce model
   */
  Optional<Sauce> get(String name);

  /**
   * Create a new Sauce given a new Sauce model.
   *
   * Note must be uniquely named
   * @param sauce - sauce to create
   * @return The newly created Sauce
   */
  Sauce create(Sauce sauce);

  /**
   * Updates the Sauce given a model with
   * a matching Sauce name
   * @param sauce - sauce to update
   * @return - updated sauce
   */
  Sauce update(Sauce sauce);

  /**
   * Deletes a Sauce given the Sauce's name
   * @param name - name of sauce to delete
   */
  void delete(String name);
}
