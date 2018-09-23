package org.ryank.pizza.create.resources.pizza.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.cheese.service.CheeseService;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.ryank.pizza.create.resources.config.crust.service.CrustService;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.ryank.pizza.create.resources.config.ingredient.service.IngredientService;
import org.ryank.pizza.create.resources.config.sauce.service.SauceService;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;
import org.ryank.pizza.create.resources.pizza.api.dto.CreatePizzaDto;
import org.ryank.pizza.create.resources.pizza.repository.PizzaRepository;
import org.ryank.pizza.create.resources.pizza.repository.dataobject.PizzaDO;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;

public class PizzaServiceTest {

  private PizzaService pizzaService;
  private PizzaRepository mockPizzaRepo;
  private CheeseService mockCheeseService;
  private CrustService mockCrustService;
  private SauceService mockSauceService;
  private IngredientService mockIngredientService;
  private static final Pizza TEST_PIZZA = testPizza();
  @Before
  public void setupMock() {
    mockPizzaRepo = Mockito.mock(PizzaRepository.class);
    mockCheeseService = Mockito.mock(CheeseService.class);
    mockCrustService = Mockito.mock(CrustService.class);
    mockSauceService = Mockito.mock(SauceService.class);
    mockIngredientService = Mockito.mock(IngredientService.class);
    pizzaService = new PizzaServiceImpl(mockPizzaRepo, mockCheeseService, mockCrustService, mockSauceService, mockIngredientService);
  }

  @Test
  public void get_shouldReturnPizza_whenExists() {
    Pizza expected = TEST_PIZZA;
    Mockito.when(mockPizzaRepo.findById(TEST_PIZZA.getId()))
        .thenReturn(Optional.of(new PizzaDO(expected)));

    Pizza result = pizzaService.get(TEST_PIZZA.getId()).orElseThrow();

    assertThat(result.getId(), is(expected.getId()));
  }

  @Test
  public void get_shouldReturnEmptyOptional_whenNotFound() {
    Mockito.when(mockPizzaRepo.findById(Mockito.anyInt()))
        .thenReturn(Optional.empty());

    Optional<Pizza> result = pizzaService.get(TEST_PIZZA.getId());

    assertThat(result.isPresent(), is(false));

  }

  @Test
  public void create_should_returnCreatedObject_whenValidInput() {
    Pizza expected = TEST_PIZZA;
    Mockito.when(mockPizzaRepo.save(any(PizzaDO.class)))
        .thenReturn(new PizzaDO(expected));

    Mockito.when(mockCheeseService.get(TEST_PIZZA.getCheese().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCheese()));
    Mockito.when(mockCrustService.get(TEST_PIZZA.getCrust().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCrust()));
    Mockito.when(mockSauceService.get(TEST_PIZZA.getSauce().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getSauce()));

    CreatePizzaDto input = new CreatePizzaDto(TEST_PIZZA.getCheese().getName(),
        TEST_PIZZA.getCrust().getName(), TEST_PIZZA.getSauce().getName(), Collections.emptyList());

    Pizza resultCheese = pizzaService.create(input);

    assertThat(resultCheese, is(expected));
  }

  @Test
  public void create_should_throwBadRequest_whenUnsupportedCheese() {

    CreatePizzaDto input = new CreatePizzaDto(TEST_PIZZA.getCheese().getName(),
        TEST_PIZZA.getCrust().getName(), TEST_PIZZA.getSauce().getName(), Collections.emptyList());

    try {
      pizzaService.create(input);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is(TEST_PIZZA.getCheese().getName() + " not supported"));
    }
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenUnsupportedCrust() {
    Mockito.when(mockCheeseService.get(TEST_PIZZA.getCheese().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCheese()));

    CreatePizzaDto input = new CreatePizzaDto(TEST_PIZZA.getCheese().getName(),
        TEST_PIZZA.getCrust().getName(), TEST_PIZZA.getSauce().getName(), Collections.emptyList());

    try {
      pizzaService.create(input);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is(TEST_PIZZA.getCrust().getName() + " not supported"));
      throw e;
    }
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenUnsupportedSauce() {
    Mockito.when(mockCheeseService.get(TEST_PIZZA.getCheese().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCheese()));
    Mockito.when(mockCrustService.get(TEST_PIZZA.getCrust().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCrust()));

    CreatePizzaDto input = new CreatePizzaDto(TEST_PIZZA.getCheese().getName(),
        TEST_PIZZA.getCrust().getName(), TEST_PIZZA.getSauce().getName(), Collections.emptyList());

    try {
      pizzaService.create(input);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is(TEST_PIZZA.getSauce().getName() + " not supported"));
      throw e;
    }
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenUnsupportedIngredient() {
    Mockito.when(mockCheeseService.get(TEST_PIZZA.getCheese().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCheese()));
    Mockito.when(mockCrustService.get(TEST_PIZZA.getCrust().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getCrust()));
    Mockito.when(mockSauceService.get(TEST_PIZZA.getSauce().getName()))
        .thenReturn(Optional.ofNullable(TEST_PIZZA.getSauce()));

    CreatePizzaDto input = new CreatePizzaDto(TEST_PIZZA.getCheese().getName(),
        TEST_PIZZA.getCrust().getName(), TEST_PIZZA.getSauce().getName(), Collections.singletonList("INGREDIENT"));

    try {
      pizzaService.create(input);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("INGREDIENT" + " not supported"));
      throw e;
    }
  }


  @Test
  public void delete_should_callDeleteMethod_whenCheeseExists() {
    Mockito.when(mockPizzaRepo.findById(anyInt()))
        .thenReturn(Optional.of(new PizzaDO(TEST_PIZZA)));

    pizzaService.delete(TEST_PIZZA.getId());

    Mockito.verify(mockPizzaRepo, Mockito.times(1)).deleteById(TEST_PIZZA.getId());
  }


  @Test(expected = BadRequestException.class)
  public void delete_should_throwBadRequest_whenCheeseDoesntExists() {

    try {
      pizzaService.delete(TEST_PIZZA.getId());
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Pizza with id " + TEST_PIZZA.getId() + " does not exist"));
      throw e;
    }
  }

  private static Pizza testPizza() {
    return Pizza.builder()
        .id(1)
        .cheese(new Cheese("CHEESE", 0.0))
        .crust(new Crust("CRUST", 0.0))
        .sauce(new Sauce("SAUCE", 0.0))
        .ingredients(Collections.emptyList())
        .build();
  }
}