package org.ryank.pizza.create.resources.config.cheese.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.cheese.repository.CheeseRepository;
import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.CheeseDO;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheeseServiceImpl implements CheeseService {

  private final CheeseRepository repository;

  @Override
  public List<Cheese> get() {
    return repository.findAll().stream()
        .map(CheeseDO::unpack)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Cheese> get(String name) {
    return repository.findByNameIgnoreCase(name)
        .map(CheeseDO::unpack);
  }

  @Override
  public Cheese create(Cheese cheese) {
    assertNotExists(cheese.getName());
    return persistModel(cheese);
  }

  @Override
  public Cheese update(Cheese cheese) {
    assertExists(cheese.getName());
    return persistModel(cheese);
  }

  @Override
  public void delete(String name) {
    assertExists(name);
    repository.deleteByNameIgnoreCase(name);
  }

  private Cheese persistModel(Cheese cheese) {
    return repository.save(new CheeseDO(cheese)).unpack();
  }

  private void assertExists(String name) {
    get(name).orElseThrow(() -> new BadRequestException("Cheese " + name + " doesn't exist"));
  }

  private void assertNotExists(String name) {
    if (get(name).isPresent()) {
      throw new BadRequestException("Cheese " + name + " already exists");
    }
  }
}
