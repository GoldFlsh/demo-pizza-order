package org.ryank.pizza.create.resources.config.crust.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.resources.config.crust.api.dto.CrustDto;
import org.ryank.pizza.create.resources.config.crust.service.CrustService;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/crust")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrustConfigController {
  private final CrustService crustService;

  @GetMapping
  public ResponseEntity<List<CrustDto>> get() {
    List<CrustDto> dtos = crustService.get().stream()
        .map(CrustDto::new)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping(params = "name")
  public ResponseEntity<CrustDto> get(@RequestParam String name) {
    CrustDto dto = crustService.get(name)
        .map(CrustDto::new)
        .orElse(null);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CrustDto> create(@RequestBody CrustDto crustDto) {
    Crust model = crustService.create(crustDto.unpack());
    CrustDto dto = new CrustDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PutMapping(path = "/{name}", params = "cost")
  public ResponseEntity<CrustDto> update(@PathVariable String name, @RequestParam double cost) {
    Crust model = crustService.update(new Crust(name, cost));
    CrustDto dto = new CrustDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{name}")
  public ResponseEntity delete(@PathVariable String name) {
    crustService.delete(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}