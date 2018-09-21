package org.ryank.demopizzaorder.resources.pizza.repository;

import org.ryank.demopizzaorder.resources.pizza.repository.dataobject.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

}
