package org.ryank.pizza.create.resources.config.sauce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.sauce.repository.SauceRepository;
import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SauceServiceImpl implements SauceService {

  private final SauceRepository repository;

  @Override
  public List<Sauce> get() {
    return repository.findAll().stream()
        .map(SauceDO::unpack)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Sauce> get(String name) {
    return repository.findById(name)
        .map(SauceDO::unpack);
  }

  @Override
  public Sauce create(Sauce sauce) {
    if (get(sauce.getName()).isPresent()) {
      throw new BadRequestException("Sauce " + sauce.getName() + " already exists");
    }
    return persistModel(sauce);
  }

  @Override
  public Sauce update(Sauce sauce) {
    if (!get(sauce.getName()).isPresent()) {
      throw new BadRequestException("Sauce " + sauce.getName() + " doesn't exist");
    }
    return persistModel(sauce);
  }

  @Override
  public Sauce delete(String name) {
    return repository.deleteByName(name)
        .map(SauceDO::unpack)
        .orElseThrow(() -> new BadRequestException("Sauce " + name + " doesn't exist"));
  }

  private Sauce persistModel(Sauce Sauce) {
    return repository.save(new SauceDO(Sauce)).unpack();
  }
}
