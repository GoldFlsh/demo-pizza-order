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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
    return repository.findByNameIgnoreCase(name)
        .map(SauceDO::unpack);
  }

  @Override
  public Sauce create(Sauce sauce) {
    assertNotExists(sauce.getName());
    return persistModel(sauce);
  }

  @Override
  public Sauce update(Sauce sauce) {
    assertExists(sauce.getName());
    return persistModel(sauce);
  }

  @Override
  public void delete(String name) {
    assertExists(name);
    repository.deleteByNameIgnoreCase(name);
  }

  private Sauce persistModel(Sauce Sauce) {
    return repository.save(new SauceDO(Sauce)).unpack();
  }

  private void assertExists(String name) {
    get(name).orElseThrow(() -> new BadRequestException("Sauce " + name + " doesn't exist"));
  }

  private void assertNotExists(String name) {
    if (get(name).isPresent()) {
      throw new BadRequestException("Sauce " + name + " already exists");
    }
  }
}
