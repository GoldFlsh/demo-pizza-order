package org.ryank.pizza.create.resources.config.sauce.repository;

import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.Cheese;
import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepository extends JpaRepository<SauceDO, String> {

}
