package org.ryank.pizza.create.resources.config.sauce.api;

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
import org.ryank.pizza.create.DemoPizzaCreateApplication;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.common.exceptions.ConflictException;
import org.ryank.pizza.create.resources.config.sauce.api.dto.SauceDto;
import org.ryank.pizza.create.resources.config.sauce.service.SauceService;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;
import org.ryank.pizza.create.test.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {DemoPizzaCreateApplication.class, SauceConfigController.class})
public class SauceControllerTest {

  private static final Sauce TEST_SAUCE = new Sauce("SAUCE", 0.0);
  private static final String JSON_PATH_EXPECTING_SAUCE = "$[?(@.name=='SAUCE' && @.cost==0)]";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SauceService service;

  @Test
  public void get_should_returnStatus200WithResult_whenServiceReturnsSauce() throws Exception {

    Mockito.when(service.get()).thenReturn(Collections.singletonList(TEST_SAUCE));

    mockMvc.perform(get("/sauce"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).exists());
  }

  @Test
  public void get_should_returnStatus200WithEmptyResultSet_whenServiceReturnsEmptyResult() throws Exception {

    Mockito.when(service.get())
        .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/sauce"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).doesNotExist());
  }

  @Test
  public void getWithParam_should_returnStatus200WithSauce_whenServiceReturnsSauce() throws Exception {
    Mockito.when(service.get(TEST_SAUCE.getName())).thenReturn(Optional.of(TEST_SAUCE));

    mockMvc.perform(get("/sauce").param("name", TEST_SAUCE.getName()))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).exists());
  }

  @Test
  public void create_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.create(TEST_SAUCE)).thenReturn(TEST_SAUCE);


    mockMvc.perform(post("/sauce")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new SauceDto(TEST_SAUCE))))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).exists());
  }

  @Test
  public void create_should_returnWithStatus409_whenResourceConflict() throws Exception {
    Mockito.when(service.create(TEST_SAUCE)).thenThrow(new ConflictException());


    mockMvc.perform(post("/sauce")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new SauceDto(TEST_SAUCE))))
        .andExpect(status().is(HttpStatus.CONFLICT.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).doesNotExist());
  }

  @Test
  public void update_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.update(TEST_SAUCE)).thenReturn(TEST_SAUCE);

    mockMvc.perform(put("/sauce/"+TEST_SAUCE.getName())
                    .param("cost", String.valueOf(TEST_SAUCE.getCost())))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).exists());
  }

  @Test
  public void update_should_returnWithStatus404_whenNotFound() throws Exception {
    Mockito.when(service.update(TEST_SAUCE)).thenThrow(new BadRequestException());

    mockMvc.perform(put("/sauce/"+TEST_SAUCE.getName())
                    .param("cost", String.valueOf(TEST_SAUCE.getCost())))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_SAUCE).doesNotExist());
  }

  @Test
  public void update_should_return204_whenSuccess() throws Exception {

    mockMvc.perform(delete("/sauce/"+TEST_SAUCE.getName()))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
  }

  @Test
  public void update_should_return404_whenResourceNotFound() throws Exception {

    Mockito.doThrow(new BadRequestException()).when(service).delete(TEST_SAUCE.getName());

    mockMvc.perform(delete("/sauce/"+TEST_SAUCE.getName()))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }
}