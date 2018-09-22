package org.ryank.pizza.create.resources.config.sauce.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.sauce.repository.SauceRepository;
import org.ryank.pizza.create.resources.config.sauce.repository.dataobject.SauceDO;
import org.ryank.pizza.create.resources.config.sauce.service.model.Sauce;

public class SauceServiceTest {

  private SauceService sauceService;
  private SauceRepository mock;

  @Before
  public void setupMock() {
    mock = Mockito.mock(SauceRepository.class);
    sauceService = new SauceServiceImpl(mock);
  }

  @Test
  public void getAll_should_returnAll_whenThereIsResultsFromRepository() {
    Sauce expectedSauce = new Sauce("SAUCE", 1.0);
    Mockito.when(mock.findAll())
        .thenReturn(Collections.singletonList(new SauceDO(expectedSauce)));

    List<Sauce> resultSauces = sauceService.get();

    assertThat(resultSauces.get(0), is(expectedSauce));
  }

  @Test
  public void getAll_should_returnEmpty_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findAll()).thenReturn(Collections.emptyList());

    List<Sauce> resultSauces = sauceService.get();

    assertThat(resultSauces.size(), is(0));
  }

  @Test
  public void get_should_returnSauce_whenResultIsFound() {
    Sauce expectedSauce = new Sauce("SAUCE", 1.0);
    Mockito.when(mock.findByNameIgnoreCase(any(String.class)))
        .thenReturn(Optional.of(new SauceDO(expectedSauce)));

    Optional<Sauce> resultSauce = sauceService.get(expectedSauce.getName());

    assertThat(resultSauce.orElseThrow(), is(expectedSauce));
  }

  @Test
  public void get_should_returnEmptyOptional_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findByNameIgnoreCase(any(String.class))).thenReturn(Optional.empty());

    Optional<Sauce> resultSauce = sauceService.get("SAUCE");

    assertThat(resultSauce.isPresent(), is(false));
  }

  @Test
  public void create_should_returnCreatedObject_whenValidInput() {
    Sauce expectedSauce = new Sauce("SAUCE", 1.0);
    Mockito.when(mock.save(any(SauceDO.class)))
        .thenAnswer(AdditionalAnswers.returnsFirstArg());

    Sauce resultSauce = sauceService.create(expectedSauce);

    assertThat(resultSauce, is(expectedSauce));
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenSauceAlreadyExists() {
    Sauce existingSauce = new Sauce("SAUCE", 1.0);
    Mockito.when(mock.findByNameIgnoreCase(any(String.class)))
        .thenReturn(Optional.of(new SauceDO(existingSauce)));

    try {
      sauceService.create(existingSauce);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Sauce " + existingSauce.getName() + " already exists"));
      throw e;
    }
  }

  @Test
  public void update_should_returnUpdatedObject_whenValidInput() {
    Sauce expectedSauce = new Sauce("SAUCE", 1.0);
    Mockito.when(mock.save(any(SauceDO.class)))
        .thenAnswer(AdditionalAnswers.returnsFirstArg());
    Mockito.when(mock.findByNameIgnoreCase(expectedSauce.getName()))
        .thenReturn(Optional.of(new SauceDO(expectedSauce)));

    Sauce resultSauce = sauceService.update(expectedSauce);

    assertThat(resultSauce, is(expectedSauce));
  }

  @Test(expected = BadRequestException.class)
  public void update_should_throwBadRequest_whenDoesntExist() {
    Sauce expectedSauce = new Sauce("SAUCE", 1.0);
    try {
      sauceService.update(expectedSauce);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Sauce " + "SAUCE" + " doesn't exist"));
      throw e;
    }
  }
  
  @Test
  public void delete_should_callDeleteMethod_whenExists() {
    Sauce expectedSauce = new Sauce("SAUCE", 1.0);
    Mockito.when(mock.findByNameIgnoreCase(any(String.class)))
        .thenReturn(Optional.of(new SauceDO(expectedSauce)));

    sauceService.delete(expectedSauce.getName());

    verify(mock, times(1)).deleteByNameIgnoreCase("SAUCE");
  }

  @Test(expected = BadRequestException.class)
  public void delete_should_throwBadRequest_whenSauceDoesntExists() {

    try {
      sauceService.delete("SAUCE");
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Sauce " + "SAUCE" + " doesn't exist"));
      throw e;
    }
  }
}