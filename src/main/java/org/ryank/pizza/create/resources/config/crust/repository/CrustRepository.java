package org.ryank.pizza.create.resources.config.crust.repository;

import java.util.Optional;
import org.ryank.pizza.create.resources.config.crust.repository.dataobject.CrustDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrustRepository extends JpaRepository<CrustDO, String> {
  Optional<CrustDO> findByNameIgnoreCase(String name);
  void deleteByNameIgnoreCase(String name);

}
