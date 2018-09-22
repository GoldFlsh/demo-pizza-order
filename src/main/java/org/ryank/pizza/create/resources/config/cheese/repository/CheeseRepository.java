package org.ryank.pizza.create.resources.config.cheese.repository;

import java.util.Optional;
import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.CheeseDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheeseRepository extends JpaRepository<CheeseDO, String> {

  Optional<CheeseDO> deleteByName(String name);

}
