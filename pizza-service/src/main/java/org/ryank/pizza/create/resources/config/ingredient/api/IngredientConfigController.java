package org.ryank.pizza.create.resources.config.ingredient.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.resources.config.ingredient.service.IngredientService;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.ryank.pizza.create.resources.config.ingredient.api.dto.IngredientDto;
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
@RequestMapping(value = "/ingredient")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IngredientConfigController {
  private final IngredientService ingredientService;

  //TODO Secure such that only administrators can access

  @GetMapping
  public ResponseEntity<List<IngredientDto>> get() {
    List<IngredientDto> dtos = ingredientService.get().stream()
        .map(IngredientDto::new)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping(params = "name")
  public ResponseEntity<IngredientDto> get(@RequestParam String name) {
    IngredientDto dto = ingredientService.get(name)
        .map(IngredientDto::new)
        .orElse(null);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<IngredientDto> create(@RequestBody IngredientDto ingredientDto) {
    Ingredient model = ingredientService.create(ingredientDto.unpack());
    IngredientDto dto = new IngredientDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PutMapping(path = "/{name}", params = "cost")
  public ResponseEntity<IngredientDto> update(@PathVariable String name, @RequestParam double cost) {
    Ingredient model = ingredientService.update(new Ingredient(name, cost));
    IngredientDto dto = new IngredientDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{name}")
  public ResponseEntity delete(@PathVariable String name) {
    ingredientService.delete(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}