package org.ryank.pizza.create.resources.pizza.api;

import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.pizza.api.dto.CreatePizzaDto;
import org.ryank.pizza.create.resources.pizza.api.dto.IdDto;
import org.ryank.pizza.create.resources.pizza.api.dto.PizzaDto;
import org.ryank.pizza.create.resources.pizza.service.PizzaService;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PizzaBuilderController {

  private static final String ID_PATH_WITHOUT_SWAGGER_MATCH = "/{id:[!swagger-ui.html]}";
  private final PizzaService pizzaService;

  @GetMapping(path = ID_PATH_WITHOUT_SWAGGER_MATCH)
  public ResponseEntity<PizzaDto> get(@PathVariable Integer id) {
    return pizzaService.get(id)
        .map(PizzaDto::new)
        .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
        .orElseThrow(() -> new BadRequestException("Could not find pizza with id " + id));
  }

  @PostMapping
  public ResponseEntity<IdDto> create(@RequestBody CreatePizzaDto createPizzaDto) {
    Pizza model = pizzaService.create(createPizzaDto);
    IdDto idDto = new IdDto(model.getId());
    return new ResponseEntity<>(idDto, HttpStatus.CREATED);
  }

  @DeleteMapping(path = ID_PATH_WITHOUT_SWAGGER_MATCH)
  public ResponseEntity<PizzaDto> delete(@PathVariable Integer id) {
    pizzaService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
