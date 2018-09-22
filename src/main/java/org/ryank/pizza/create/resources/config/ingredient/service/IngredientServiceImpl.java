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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
    return repository.findByNameIgnoreCase(name)
        .map(IngredientDO::unpack);
  }

  @Override
  public Ingredient create(Ingredient ingredient) {
    assertNotExists(ingredient.getName());
    return persistModel(ingredient);
  }

  @Override
  public Ingredient update(Ingredient ingredient) {
    assertExists(ingredient.getName());
    return persistModel(ingredient);
  }

  @Override
  public void delete(String name) {
    assertExists(name);
    repository.deleteByNameIgnoreCase(name);
  }

  private Ingredient persistModel(Ingredient Ingredient) {
    return repository.save(new IngredientDO(Ingredient)).unpack();
  }

  private void assertExists(String name) {
    get(name).orElseThrow(() -> new BadRequestException("Ingredient " + name + " doesn't exist"));
  }

  private void assertNotExists(String name) {
    if (get(name).isPresent()) {
      throw new BadRequestException("Ingredient " + name + " already exists");
    }
  }
}
