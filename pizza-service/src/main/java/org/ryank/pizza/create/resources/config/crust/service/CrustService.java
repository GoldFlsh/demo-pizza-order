package org.ryank.pizza.create.resources.config.crust.service;

import java.util.List;
import java.util.Optional;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;

public interface CrustService {

  /**
   * Return all supported Crusts
   * @return list of all configured crusts
   */
  List<Crust> get();

  /**
   * Get a specific Crust by name
   * @param name - name of crust to find
   * @return crust model found
   */
  Optional<Crust> get(String name);

  /**
   * Create a new Crust given a new Crust model.
   *
   * name must be unique
   * @param crust - the crust to create
   * @return The newly created Crust
   */
  Crust create(Crust crust);

  /**
   * Updates the Crust given a model with
   * a matching Crust name
   * @param crust - the crust to update
   * @return - the updated crust
   */
  Crust update(Crust crust);

  /**
   * Deletes a Crust given the Crust's name
   * @param name - name of the crust to delete
   */
  void delete(String name);
}
