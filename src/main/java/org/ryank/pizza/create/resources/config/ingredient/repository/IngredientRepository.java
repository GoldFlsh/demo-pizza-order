package org.ryank.pizza.create.resources.config.ingredient.repository;

import java.util.Optional;
import org.ryank.pizza.create.resources.config.ingredient.repository.dataobject.IngredientDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientDO, String> {

  Optional<IngredientDO> deleteByName(String name);
}
