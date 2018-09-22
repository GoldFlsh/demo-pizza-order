package org.ryank.pizza.create.resources.config.crust.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;

public interface CrustService {

  /**
   * Return all supported Crusts
   * @return
   */
  List<Crust> get();

  /**
   * Get a specific Crust by name
   * @param name
   * @return Crust model
   */
  Optional<Crust> get(String name);

  /**
   * Create a new Crust given a new Crust model.
   *
   * Note must be uniquely named
   * @param Crust
   * @return The newly created Crust
   */
  Crust create(Crust Crust);

  /**
   * Updates the Crust given a model with
   * a matching Crust name
   * @param Crust
   * @return
   */
  Crust update(Crust Crust);

  /**
   * Deletes a Crust given the Crust's name
   * @param name
   * @return the deleted Crust
   */
  Crust delete(String name);
}
