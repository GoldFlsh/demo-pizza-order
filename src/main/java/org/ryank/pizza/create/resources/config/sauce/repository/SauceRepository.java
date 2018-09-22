package org.ryank.pizza.create.resources.config.sauce.repository;

import java.util.Optional;
import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepository extends JpaRepository<SauceDO, String> {

  Optional<SauceDO> deleteByName(String name);
}
