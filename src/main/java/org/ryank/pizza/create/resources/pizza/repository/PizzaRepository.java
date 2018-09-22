package org.ryank.pizza.create.resources.pizza.repository;

import org.ryank.pizza.create.resources.pizza.repository.dataobject.PizzaDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaDO, Integer> {

}
