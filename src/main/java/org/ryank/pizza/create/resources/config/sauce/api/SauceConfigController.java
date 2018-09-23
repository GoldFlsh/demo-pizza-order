package org.ryank.pizza.create.resources.config.sauce.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.resources.config.sauce.service.SauceService;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;
import org.ryank.pizza.create.resources.config.sauce.api.dto.SauceDto;
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
@RequestMapping(value = "/sauce")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SauceConfigController {
  private final SauceService sauceService;

  //TODO Secure such that only administrators can access

  @GetMapping
  public ResponseEntity<List<SauceDto>> get() {
    List<SauceDto> dtos = sauceService.get().stream()
        .map(SauceDto::new)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping(params = "name")
  public ResponseEntity<SauceDto> get(@RequestParam String name) {
    SauceDto dto = sauceService.get(name)
        .map(SauceDto::new)
        .orElse(null);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SauceDto> create(@RequestBody SauceDto sauceDto) {
    Sauce model = sauceService.create(sauceDto.unpack());
    SauceDto dto = new SauceDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PutMapping(path = "/{name}", params = "cost")
  public ResponseEntity<SauceDto> update(@PathVariable String name, @RequestParam double cost) {
    Sauce model = sauceService.update(new Sauce(name, cost));
    SauceDto dto = new SauceDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{name}")
  public ResponseEntity delete(@PathVariable String name) {
    sauceService.delete(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}