package org.ryank.pizza.create.resources.config.cheese.api;

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
import org.ryank.pizza.create.resources.config.cheese.api.dto.CheeseDto;
import org.ryank.pizza.create.resources.config.cheese.service.CheeseService;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;
import org.ryank.pizza.create.test.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {CheeseConfigController.class})
public class CheeseControllerTest {

  private static final Cheese TEST_CHEESE = new Cheese("CHEESE", 0.0);
  private static final String JSON_PATH_EXPECTING_CHEESE = "$[?(@.name=='CHEESE' && @.cost==0)]";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CheeseService service;

  @Test
  public void get_should_returnStatus200WithResult_whenServiceReturnsCheese() throws Exception {

    Mockito.when(service.get()).thenReturn(Collections.singletonList(TEST_CHEESE));

    mockMvc.perform(get("/cheese"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).exists());
  }

  @Test
  public void get_should_returnStatus200WithEmptyResultSet_whenServiceReturnsEmptyResult() throws Exception {

    Mockito.when(service.get())
        .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/cheese"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).doesNotExist());
  }

  @Test
  public void getWithParam_should_returnStatus200WithCheese_whenServiceReturnsCheese() throws Exception {
    Mockito.when(service.get(TEST_CHEESE.getName())).thenReturn(Optional.of(TEST_CHEESE));

    mockMvc.perform(get("/cheese").param("name", TEST_CHEESE.getName()))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).exists());
  }

  @Test
  public void getWithParam_should_returnStatus409_whenServiceDoesntReturnCheese() throws Exception {
    Mockito.when(service.get(TEST_CHEESE.getName())).thenReturn(Optional.empty());

    mockMvc.perform(get("/cheese").param("name", TEST_CHEESE.getName()))
        .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).doesNotExist());
  }

  @Test
  public void create_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.create(TEST_CHEESE)).thenReturn(TEST_CHEESE);


    mockMvc.perform(post("/cheese")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new CheeseDto(TEST_CHEESE))))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).exists());
  }

  @Test
  public void create_should_returnWithStatus409_whenResourceConflict() throws Exception {
    Mockito.when(service.create(TEST_CHEESE)).thenThrow(new ConflictException());


    mockMvc.perform(post("/cheese")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new CheeseDto(TEST_CHEESE))))
        .andExpect(status().is(HttpStatus.CONFLICT.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).doesNotExist());
  }

  @Test
  public void update_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.update(TEST_CHEESE)).thenReturn(TEST_CHEESE);

    mockMvc.perform(put("/cheese/"+TEST_CHEESE.getName())
                    .param("cost", String.valueOf(TEST_CHEESE.getCost())))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).exists());
  }

  @Test
  public void update_should_returnWithStatus404_whenNotFound() throws Exception {
    Mockito.when(service.update(TEST_CHEESE)).thenThrow(new BadRequestException());

    mockMvc.perform(put("/cheese/"+TEST_CHEESE.getName())
                    .param("cost", String.valueOf(TEST_CHEESE.getCost())))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CHEESE).doesNotExist());
  }

  @Test
  public void update_should_return204_whenSuccess() throws Exception {

    mockMvc.perform(delete("/cheese/"+TEST_CHEESE.getName()))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
  }

  @Test
  public void update_should_return404_whenResourceNotFound() throws Exception {

    Mockito.doThrow(new BadRequestException()).when(service).delete(TEST_CHEESE.getName());

    mockMvc.perform(delete("/cheese/"+TEST_CHEESE.getName()))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }
}
