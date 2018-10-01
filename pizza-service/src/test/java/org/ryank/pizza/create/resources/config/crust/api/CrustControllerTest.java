package org.ryank.pizza.create.resources.config.crust.api;

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
import org.ryank.pizza.create.resources.config.crust.api.dto.CrustDto;
import org.ryank.pizza.create.resources.config.crust.service.CrustService;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;
import org.ryank.pizza.create.test.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {CrustConfigController.class})
public class CrustControllerTest {

  private static final Crust TEST_CRUST = new Crust("CRUST", 0.0);
  private static final String JSON_PATH_EXPECTING_CRUST = "$[?(@.name=='CRUST' && @.cost==0)]";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CrustService service;

  @Test
  public void get_should_returnStatus200WithResult_whenServiceReturnsCrust() throws Exception {

    Mockito.when(service.get()).thenReturn(Collections.singletonList(TEST_CRUST));

    mockMvc.perform(get("/crust"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).exists());
  }

  @Test
  public void get_should_returnStatus200WithEmptyResultSet_whenServiceReturnsEmptyResult() throws Exception {

    Mockito.when(service.get())
        .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/crust"))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).doesNotExist());
  }

  @Test
  public void getWithParam_should_returnStatus200WithCrust_whenServiceReturnsCrust() throws Exception {
    Mockito.when(service.get(TEST_CRUST.getName())).thenReturn(Optional.of(TEST_CRUST));

    mockMvc.perform(get("/crust").param("name", TEST_CRUST.getName()))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).exists());
  }

  @Test
  public void create_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.create(TEST_CRUST)).thenReturn(TEST_CRUST);


    mockMvc.perform(post("/crust")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new CrustDto(TEST_CRUST))))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).exists());
  }

  @Test
  public void create_should_returnWithStatus409_whenResourceConflict() throws Exception {
    Mockito.when(service.create(TEST_CRUST)).thenThrow(new ConflictException());


    mockMvc.perform(post("/crust")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonMapper.objectToJson(new CrustDto(TEST_CRUST))))
        .andExpect(status().is(HttpStatus.CONFLICT.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).doesNotExist());
  }

  @Test
  public void update_should_returnWithStatus200_whenSuccess() throws Exception {
    Mockito.when(service.update(TEST_CRUST)).thenReturn(TEST_CRUST);

    mockMvc.perform(put("/crust/"+TEST_CRUST.getName())
                    .param("cost", String.valueOf(TEST_CRUST.getCost())))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).exists());
  }

  @Test
  public void update_should_returnWithStatus404_whenNotFound() throws Exception {
    Mockito.when(service.update(TEST_CRUST)).thenThrow(new BadRequestException());

    mockMvc.perform(put("/crust/"+TEST_CRUST.getName())
                    .param("cost", String.valueOf(TEST_CRUST.getCost())))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath(JSON_PATH_EXPECTING_CRUST).doesNotExist());
  }

  @Test
  public void update_should_return204_whenSuccess() throws Exception {

    mockMvc.perform(delete("/crust/"+TEST_CRUST.getName()))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
  }

  @Test
  public void update_should_return404_whenResourceNotFound() throws Exception {

    Mockito.doThrow(new BadRequestException()).when(service).delete(TEST_CRUST.getName());

    mockMvc.perform(delete("/crust/"+TEST_CRUST.getName()))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }
}