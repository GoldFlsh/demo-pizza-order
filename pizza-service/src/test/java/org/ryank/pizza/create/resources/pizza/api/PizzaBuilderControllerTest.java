package org.ryank.pizza.create.resources.pizza.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;
import org.ryank.pizza.create.resources.pizza.api.dto.CreatePizzaDto;
import org.ryank.pizza.create.resources.pizza.service.PizzaService;
import org.ryank.pizza.create.resources.pizza.service.model.Pizza;
import org.ryank.pizza.create.test.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PizzaBuilderController.class)
public class PizzaBuilderControllerTest {

  private static final Integer TEST_PIZZA_ID = 1;
  private static final Cheese TEST_CHEESE = new Cheese("TEST_CHEESE", 0.0);
  private static final Sauce TEST_SAUCE = new Sauce("TEST_SAUCE", 0.0);
  private static final Crust TEST_CRUST = new Crust("TEST_CRUST", 0.0);
  private static final Ingredient TEST_INGREDIENT = new Ingredient("TEST_INGREDIENT", 0.0);
  private static final Pizza TEST_PIZZA_MODEL =
      new Pizza(TEST_PIZZA_ID, TEST_CHEESE, TEST_CRUST, TEST_SAUCE, Collections.singletonList(TEST_INGREDIENT));

  @Autowired
  MockMvc mockMvc;

  @MockBean
  PizzaService service;

  @Test
  public void get_ById_should_returnPizza_ifFound() throws Exception {
    Mockito.when(service.get(TEST_PIZZA_ID)).thenReturn(Optional.of(TEST_PIZZA_MODEL));

    mockMvc.perform(get("/"+TEST_PIZZA_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(TEST_PIZZA_ID))
        .andExpect(jsonPath("$.cheese.name").value(TEST_CHEESE.getName()))
        .andExpect(jsonPath("$.sauce.name").value(TEST_SAUCE.getName()))
        .andExpect(jsonPath("$.crust.name").value(TEST_CRUST.getName()))
        .andExpect(jsonPath("$.ingredients[0].name").value(TEST_INGREDIENT.getName()));
  }

  @Test
  public void get_ById_should_returnStatus400_ifNotFound() throws Exception {
    Mockito.when(service.get(TEST_PIZZA_ID)).thenReturn(Optional.empty());

    mockMvc.perform(get("/"+TEST_PIZZA_ID))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Could not find pizza with id " + TEST_PIZZA_ID));
  }

  @Test
  public void create_should_returnStatus201AndIdOfNewPizza_ifSuccessfullyCreated() throws Exception {
    CreatePizzaDto input = new CreatePizzaDto(TEST_CHEESE.getName(), TEST_CRUST.getName(),
        TEST_SAUCE.getName(), Collections.singletonList(TEST_INGREDIENT.getName()));
    Mockito.when(service.create(input)).thenReturn(TEST_PIZZA_MODEL);

    mockMvc.perform(post("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(input)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(TEST_PIZZA_ID));
  }

  @Test
  public void delete_should_return204_ifSuccess() throws Exception {
    mockMvc.perform(delete("/"+TEST_PIZZA_ID))
        .andExpect(status().isNoContent());
  }

  @Test
  public void get_should_returnStatus404_ifNonNumericId() throws Exception {
    mockMvc.perform(get("/nonNumericId"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void delete_should_returnStatus404_ifNonNumericId() throws Exception {
    mockMvc.perform(delete("/nonNumericId"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_returnStatus500_ifUnexpectedError() throws Exception {
    Mockito.when(service.get(TEST_PIZZA_ID)).thenThrow(new RuntimeException("Unexpected error"));

    mockMvc.perform(get("/"+TEST_PIZZA_ID))
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.message").value("Unexpected error"));
  }
}
