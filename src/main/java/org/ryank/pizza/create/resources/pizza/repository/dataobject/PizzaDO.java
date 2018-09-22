package org.ryank.pizza.create.resources.pizza.repository.dataobject;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.CheeseDO;
import org.ryank.pizza.create.resources.config.crust.repository.dataobject.CrustDO;
import org.ryank.pizza.create.resources.config.ingredient.repository.dataobject.IngredientDO;
import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;

@Data
@Entity
public class PizzaDO {

  @Id
  @GeneratedValue
  private Integer id;

  @OneToOne
  private CheeseDO cheese;

  @OneToOne
  private CrustDO crust;

  @OneToOne
  private SauceDO sauce;

  @OneToMany
  private List<IngredientDO> ingredients;
}
