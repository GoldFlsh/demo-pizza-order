package org.ryank.pizza.create.resources.config.ingredient.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.ingredient.repository.IngredientRepository;
import org.ryank.pizza.create.resources.config.ingredient.repository.dataobject.IngredientDO;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IngredientServiceImpl implements IngredientService {

  private final IngredientRepository repository;

  @Override
  public List<Ingredient> get() {
    return repository.findAll().stream()
        .map(IngredientDO::unpack)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Ingredient> get(String name) {
    return repository.findById(name)
        .map(IngredientDO::unpack);
  }

  @Override
  public Ingredient create(Ingredient ingredient) {
    if (get(ingredient.getName()).isPresent()) {
      throw new BadRequestException("Ingredient " + ingredient.getName() + " already exists");
    }
    return persistModel(ingredient);
  }

  @Override
  public Ingredient update(Ingredient ingredient) {
    if (!get(ingredient.getName()).isPresent()) {
      throw new BadRequestException("Ingredient " + ingredient.getName() + " doesn't exist");
    }
    return persistModel(ingredient);
  }

  @Override
  public Ingredient delete(String name) {
    return repository.deleteByName(name)
        .map(IngredientDO::unpack)
        .orElseThrow(() -> new BadRequestException("Ingredient " + name + " doesn't exist"));
  }

  private Ingredient persistModel(Ingredient Ingredient) {
    return repository.save(new IngredientDO(Ingredient)).unpack();
  }
}
