package org.ryank.demopizzaorder.resources.pizza.repository.dataobject;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Pizza {

  @Id
  @GeneratedValue
  private Integer id;

  @OneToOne
  private Cheese cheese;

  @OneToOne
  private Crust crust;

  @OneToOne
  private Sauce sauce;

  @OneToMany
  private List<Ingredient> ingredients;
}
