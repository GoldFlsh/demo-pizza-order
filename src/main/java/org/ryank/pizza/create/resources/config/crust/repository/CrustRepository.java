package org.ryank.pizza.create.resources.config.crust.repository;

import org.ryank.pizza.create.resources.config.crust.repository.dataobject.Crust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrustRepository extends JpaRepository<Crust, String> {

}
