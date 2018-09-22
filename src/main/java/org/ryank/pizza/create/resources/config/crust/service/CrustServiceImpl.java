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
    return repository.findById(name)
        .map(CrustDO::unpack);
  }

  @Override
  public Crust create(Crust Crust) {
    if (get(Crust.getName()).isPresent()) {
      throw new BadRequestException("Crust " + Crust.getName() + " already exists");
    }
    return persistModel(Crust);
  }

  @Override
  public Crust update(Crust crust) {
    if (!get(crust.getName()).isPresent()) {
      throw new BadRequestException("Crust " + crust.getName() + " doesn't exist");
    }
    return persistModel(crust);
  }

  @Override
  public Crust delete(String name) {
    return repository.deleteByName(name)
        .map(CrustDO::unpack)
        .orElseThrow(() -> new BadRequestException("Crust " + name + " doesn't exist"));
  }

  private Crust persistModel(Crust Crust) {
    return repository.save(new CrustDO(Crust)).unpack();
  }
}
