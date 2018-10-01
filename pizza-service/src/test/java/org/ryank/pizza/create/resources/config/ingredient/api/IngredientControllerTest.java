package org.ryank.pizza.create.resources.config.ingredient.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.common.exceptions.ConflictException;
import org.ryank.pizza.create.resources.config.ingredient.api.dto.IngredientDto;
import org.ryank.pizza.create.resources.config.ingredient.service.IngredientService;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;
import org.ryank.pizza.create.test.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {IngredientConfigController.class})
public class IngredientControllerTest {

  private static final Ingredient TEST_INGREDIENT = new Ingredient("INGREDIENT", 0.0);
  private static final String JSON_PATH_EXPECTING_INGREDIENT = "$[?(@.name=='INGREDIENT' && @.cost==0)]";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IngredientService service;

  @Test
  public void get_should_returnStatus200WithResult_whenServiceReturnsIngredient() throws Exception {

    Mockito.when(service.get()).thenReturn(Collections.singletonList(TEST_INGREDIENT));

    mockMvc.perform(get("/ingredient"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).exists());
  }

  @Test
  public void get_should_returnStatus200WithEmptyResultSet_whenServiceReturnsEmptyResult() throws Exception {

    Mockito.when(service.get())
        .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/ingredient"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).doesNotExist());
  }

  @Test
  public void getWithParam_should_returnStatus200WithIngredient_whenServiceReturnsIngredient() throws Exception {
    Mockito.when(service.get(TEST_INGREDIENT.getName())).thenReturn(Optional.of(TEST_INGREDIENT));

    mockMvc.perform(get("/ingredient").param("name", TEST_INGREDIENT.getName()))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).exists());
  }

  @Test
  public void create_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.create(TEST_INGREDIENT)).thenReturn(TEST_INGREDIENT);


    mockMvc.perform(post("/ingredient")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new IngredientDto(TEST_INGREDIENT))))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).exists());
  }

  @Test
  public void create_should_returnWithStatus409_whenResourceConflict() throws Exception {
    Mockito.when(service.create(TEST_INGREDIENT)).thenThrow(new ConflictException());


    mockMvc.perform(post("/ingredient")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new IngredientDto(TEST_INGREDIENT))))
        .andExpect(status().is(HttpStatus.CONFLICT.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).doesNotExist());
  }

  @Test
  public void update_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.update(TEST_INGREDIENT)).thenReturn(TEST_INGREDIENT);

    mockMvc.perform(put("/ingredient/"+TEST_INGREDIENT.getName())
                    .param("cost", String.valueOf(TEST_INGREDIENT.getCost())))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).exists());
  }

  @Test
  public void update_should_returnWithStatus404_whenNotFound() throws Exception {
    Mockito.when(service.update(TEST_INGREDIENT)).thenThrow(new BadRequestException());

    mockMvc.perform(put("/ingredient/"+TEST_INGREDIENT.getName())
                    .param("cost", String.valueOf(TEST_INGREDIENT.getCost())))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_INGREDIENT).doesNotExist());
  }

  @Test
  public void update_should_return204_whenSuccess() throws Exception {

    mockMvc.perform(delete("/ingredient/"+TEST_INGREDIENT.getName()))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
  }

  @Test
  public void update_should_return404_whenResourceNotFound() throws Exception {

    Mockito.doThrow(new BadRequestException()).when(service).delete(TEST_INGREDIENT.getName());

    mockMvc.perform(delete("/ingredient/"+TEST_INGREDIENT.getName()))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }
}
