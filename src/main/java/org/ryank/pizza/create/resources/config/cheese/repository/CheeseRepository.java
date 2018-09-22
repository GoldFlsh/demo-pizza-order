package org.ryank.pizza.create.resources.config.cheese.repository;

import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.Cheese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheeseRepository extends JpaRepository<Cheese, String> {

}
