package org.ryank.pizza.create.resources.config.crust.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.crust.repository.CrustRepository;
import org.ryank.pizza.create.resources.config.crust.repository.dataobject.CrustDO;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrustServiceImpl implements CrustService {

  private final CrustRepository repository;

  @Override
  public List<Crust> get() {
    return repository.findAll().stream()
        .map(CrustDO::unpack)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Crust> get(String name) {
    return repository.findByNameIgnoreCase(name)
        .map(CrustDO::unpack);
  }

  @Override
  public Crust create(Crust crust) {
    assertNotExists(crust.getName());
    return persistModel(crust);
  }

  @Override
  public Crust update(Crust crust) {
    assertExists(crust.getName());
    return persistModel(crust);
  }

  @Override
  public void delete(String name) {
    assertExists(name);
    repository.deleteByNameIgnoreCase(name);
  }

  private Crust persistModel(Crust crust) {
    return repository.save(new CrustDO(crust)).unpack();
  }

  private void assertExists(String name) {
    get(name).orElseThrow(() -> new BadRequestException("Crust " + name + " doesn't exist"));
  }

  private void assertNotExists(String name) {
    if (get(name).isPresent()) {
      throw new BadRequestException("Crust " + name + " already exists");
    }
  }
}
