package org.ryank.pizza.create.resources.config.cheese.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.NotFoundException;
import org.ryank.pizza.create.resources.config.cheese.api.dto.CheeseDto;
import org.ryank.pizza.create.resources.config.cheese.service.CheeseService;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
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
@RequestMapping(value = "/cheese")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheeseConfigController {


  private final CheeseService cheeseService;

  @GetMapping
  public ResponseEntity<List<CheeseDto>> get() {
    List<CheeseDto> dtos = cheeseService.get().stream()
        .map(CheeseDto::new)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping(params = "name")
  public ResponseEntity<CheeseDto> get(@RequestParam String name) {
    CheeseDto dto = cheeseService.get(name)
        .map(CheeseDto::new)
        .orElseThrow(() -> new NotFoundException("Resource not defined with name " + name));
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CheeseDto> create(@RequestBody CheeseDto cheeseDto) {
    Cheese model = cheeseService.create(cheeseDto.unpack());
    CheeseDto dto = new CheeseDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PutMapping(path = "/{name}", params = "cost")
  public ResponseEntity<CheeseDto> update(@PathVariable String name, @RequestParam double cost) {
    Cheese model = cheeseService.update(new Cheese(name, cost));
    CheeseDto dto = new CheeseDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{name}")
  public ResponseEntity delete(@PathVariable String name) {
    cheeseService.delete(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}