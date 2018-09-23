package org.ryank.pizza.create.resources.pizza.repository.dataobject;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.CheeseDO;
import org.ryank.pizza.create.resources.config.crust.repository.dataobject.CrustDO;
import org.ryank.pizza.create.resources.config.ingredient.repository.dataobject.IngredientDO;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;

@Data
@Entity(name = "PIZZA")
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

  @ManyToMany(targetEntity = IngredientDO.class, fetch = FetchType.LAZY)
  private List<IngredientDO> ingredients;

  public PizzaDO(Pizza model) {
    this.id = model.getId();
    this.cheese = new CheeseDO(model.getCheese());
    this.crust = new CrustDO(model.getCrust());
    this.sauce = new SauceDO(model.getSauce());
    this.ingredients = model.getIngredients().stream()
        .map(IngredientDO::new)
        .collect(Collectors.toList());
  }

  public Pizza unpack() {
    return Pizza.builder()
        .id(id)
        .cheese(cheese.unpack())
        .crust(crust.unpack())
        .sauce(sauce.unpack())
        .ingredients(unpackIngredients())
        .build();
  }

  private List<Ingredient> unpackIngredients() {
    return ingredients.stream()
        .map(IngredientDO::unpack)
        .collect(Collectors.toList());
  }
}