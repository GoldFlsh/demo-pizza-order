package org.ryank.pizza.create.resources.config.ingredient.repository;

import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<SauceDO, String> {

}
