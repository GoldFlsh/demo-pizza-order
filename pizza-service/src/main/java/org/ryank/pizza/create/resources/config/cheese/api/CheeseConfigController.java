package org.ryank.pizza.create.resources.config.cheese.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ryank.pizza.create.common.exceptions.NotFoundException;
import org.ryank.pizza.create.resources.config.cheese.api.dto.CheeseDto;
import org.ryank.pizza.create.resources.config.cheese.service.CheeseService;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "Cheese Configuration",
    description = "Allows administrator to configure available cheeses",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping(value = "/cheese")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheeseConfigController {


  private final CheeseService cheeseService;

  @ApiOperation(httpMethod = "GET", value = "Get All Configured Cheese", nickname = "getAllCheeses")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = CheeseDto.class, responseContainer = "list")
  })
  @GetMapping
  public ResponseEntity<List<CheeseDto>> get() {
    List<CheeseDto> dtos = cheeseService.get().stream()
        .map(CheeseDto::new)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "GET", value = "Get a specific Configured Cheese", nickname = "getCheese")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Found cheese", response = CheeseDto.class),
      @ApiResponse(code = 404, message = "Cheese not found")
  })
  @GetMapping(params = "name")
  public ResponseEntity<CheeseDto> get(@RequestParam String name) {
    CheeseDto dto = cheeseService.get(name)
        .map(CheeseDto::new)
        .orElseThrow(() -> new NotFoundException("Resource not defined with name " + name));
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "POST", value = "Create a new Cheese", nickname = "createCheese")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Created cheese", response = CheeseDto.class),
      @ApiResponse(code = 409, message = "Cheese already exists")
  })
  @PostMapping
  public ResponseEntity<CheeseDto> create(@RequestBody CheeseDto cheeseDto) {
    Cheese model = cheeseService.create(cheeseDto.unpack());
    CheeseDto dto = new CheeseDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "PUT", value = "Update existing Cheese", nickname = "updateCheese")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Updated cheese"),
      @ApiResponse(code = 404, message = "Cheese not found")
  })
  @PutMapping(path = "/{name}", params = "cost")
  public ResponseEntity<CheeseDto> update(@PathVariable String name, @RequestParam double cost) {
    Cheese model = cheeseService.update(new Cheese(name, cost));
    CheeseDto dto = new CheeseDto(model);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete cheese", nickname = "deleteCheese")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Deleted cheese"),
      @ApiResponse(code = 404, message = "Cheese not found")
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping(path = "/{name}")
  public ResponseEntity delete(@PathVariable String name) {
    cheeseService.delete(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}