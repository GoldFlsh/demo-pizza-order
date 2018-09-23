package org.ryank.pizza.create.resources.pizza.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.cheese.service.CheeseService;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.ryank.pizza.create.resources.config.crust.service.CrustService;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.ryank.pizza.create.resources.config.ingredient.service.IngredientService;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.ryank.pizza.create.resources.config.sauce.service.SauceService;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;
import org.ryank.pizza.create.resources.pizza.api.dto.CreatePizzaDto;
import org.ryank.pizza.create.resources.pizza.repository.PizzaRepository;
import org.ryank.pizza.create.resources.pizza.repository.dataobject.PizzaDO;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PizzaServiceImpl implements PizzaService {

  private final PizzaRepository repository;
  private final CheeseService cheeseService;
  private final CrustService crustService;
  private final SauceService sauceService;
  private final IngredientService ingredientService;

  @Override
  public Optional<Pizza> get(Integer id) {
    return repository.findById(id)
        .map(PizzaDO::unpack);
  }

  @Override
  public Pizza create(CreatePizzaDto newPizzaDto) {
    Cheese cheese = getCheese(newPizzaDto.getCheese());
    Crust crust = getCrust(newPizzaDto.getCrust());
    Sauce sauce = getSauce(newPizzaDto.getSauce());
    List<Ingredient> ingredients = newPizzaDto.getIngredients().stream()
        .map(this::getIngredient)
        .collect(Collectors.toList());

    Pizza newPizzaModel = new Pizza(cheese, crust, sauce, ingredients);

    return persistModel(newPizzaModel);
  }

  @Override
  public void delete(Integer id) {
    assertExists(id);
    repository.deleteById(id);
  }

  private void assertExists(Integer id) {
    get(id).orElseThrow(() -> new BadRequestException("Pizza with id " + id + " does not exist"));
  }

  private Pizza persistModel(Pizza pizza) {
    return repository.save(new PizzaDO(pizza)).unpack();
  }

  private Cheese getCheese(String cheese) {
    return cheeseService
        .get(cheese)
        .orElseThrow(() -> new BadRequestException(cheese + " not supported"));
  }

  private Crust getCrust(String crust) {
    return crustService
        .get(crust)
        .orElseThrow(() -> new BadRequestException(crust + " not supported"));
  }

  private Sauce getSauce(String sauce) {
    return sauceService
        .get(sauce)
        .orElseThrow(() -> new BadRequestException(sauce + " not supported"));
  }

  private Ingredient getIngredient(String ingredient) {
    return ingredientService
        .get(ingredient)
        .orElseThrow(() -> new BadRequestException(ingredient + " not supported"));
  }
}
