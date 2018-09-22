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

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
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
    return repository.findById(name)
        .map(CheeseDO::unpack);
  }

  @Override
  public Cheese create(Cheese cheese) {
    if (get(cheese.getName()).isPresent()) {
      throw new BadRequestException("Cheese " + cheese.getName() + " already exists");
    }
    return persistModel(cheese);
  }

  @Override
  public Cheese update(Cheese cheese) {
    if (!get(cheese.getName()).isPresent()) {
        throw new BadRequestException("Cheese " + cheese.getName() + " doesn't exist");
    }
    return persistModel(cheese);
  }

  @Override
  public Cheese delete(String name) {
    return repository.deleteByName(name)
        .map(CheeseDO::unpack)
        .orElseThrow(() -> new BadRequestException("Cheese " + name + " doesn't exist"));
  }

  private Cheese persistModel(Cheese cheese) {
    return repository.save(new CheeseDO(cheese)).unpack();
  }
}
