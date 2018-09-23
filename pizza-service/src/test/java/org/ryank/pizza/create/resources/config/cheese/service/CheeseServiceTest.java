package org.ryank.pizza.create.resources.config.cheese.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ryank.pizza.create.common.exceptions.BadRequestException;
import org.ryank.pizza.create.resources.config.cheese.repository.CheeseRepository;
import org.ryank.pizza.create.resources.config.cheese.repository.dataobject.CheeseDO;
import org.ryank.pizza.create.resources.config.cheese.service.model.Cheese;

public class CheeseServiceTest {

  private CheeseService cheeseService;
  private CheeseRepository mock;

  @Before
  public void setupMock() {
    mock = Mockito.mock(CheeseRepository.class);
    cheeseService = new CheeseServiceImpl(mock);
  }

  @Test
  public void getAll_should_returnAll_whenThereIsResultsFromRepository() {
    Cheese expectedCheese = new Cheese("CHEESE", 1.0);
    Mockito.when(mock.findAll())
        .thenReturn(Collections.singletonList(new CheeseDO(expectedCheese)));

    List<Cheese> resultCheeses = cheeseService.get();

    assertThat(resultCheeses.get(0), is(expectedCheese));
  }

  @Test
  public void getAll_should_returnEmpty_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findAll()).thenReturn(Collections.emptyList());

    List<Cheese> resultCheeses = cheeseService.get();

    assertThat(resultCheeses.size(), is(0));
  }

  @Test
  public void get_should_returnCheese_whenResultIsFound() {
    Cheese expectedCheese = new Cheese("CHEESE", 1.0);
    Mockito.when(mock.findByNameIgnoreCase(any(String.class)))
        .thenReturn(Optional.of(new CheeseDO(expectedCheese)));

    Optional<Cheese> resultCheese = cheeseService.get(expectedCheese.getName());

    assertThat(resultCheese.orElseThrow(), is(expectedCheese));
  }

  @Test
  public void get_should_returnEmptyOptional_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findByNameIgnoreCase(any(String.class))).thenReturn(Optional.empty());

    Optional<Cheese> resultCheese = cheeseService.get("CHEESE");

    assertThat(resultCheese.isPresent(), is(false));
  }

  @Test
  public void create_should_returnCreatedObject_whenValidInput() {
    Cheese expectedCheese = new Cheese("CHEESE", 1.0);
    Mockito.when(mock.save(any(CheeseDO.class)))
        .thenReturn(new CheeseDO(expectedCheese));

    Cheese resultCheese = cheeseService.create(expectedCheese);

    assertThat(resultCheese, is(expectedCheese));
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenCheeseAlreadyExists() {
    Cheese existingCheese = new Cheese("CHEESE", 1.0);
    Mockito.when(mock.findByNameIgnoreCase(any(String.class)))
        .thenReturn(Optional.of(new CheeseDO(existingCheese)));

    try {
      cheeseService.create(existingCheese);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Cheese " + existingCheese.getName() + " already exists"));
      throw e;
    }
  }

  @Test
  public void update_should_returnUpdatedObject_whenValidInput() {
    Cheese expectedCheese = new Cheese("CHEESE", 1.0);

    Mockito.when(mock.save(any(CheeseDO.class)))
        .thenReturn(new CheeseDO(expectedCheese));

    Mockito.when(mock.findByNameIgnoreCase(expectedCheese.getName()))
        .thenReturn(Optional.of(new CheeseDO(expectedCheese)));

    Cheese resultCheese = cheeseService.update(expectedCheese);

    assertThat(resultCheese, is(expectedCheese));
  }

  @Test(expected = BadRequestException.class)
  public void update_should_throwBadRequest_whenDoesntExist() {
    Cheese expectedCheese = new Cheese("CHEESE", 1.0);
    try {
      cheeseService.update(expectedCheese);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Cheese " + "CHEESE" + " doesn't exist"));
      throw e;
    }
  }

  @Test
  public void delete_should_callDeleteMethod_whenCheeseExists() {
    Cheese expectedCheese = new Cheese("CHEESE", 1.0);
    Mockito.when(mock.findByNameIgnoreCase(any(String.class)))
        .thenReturn(Optional.of(new CheeseDO(expectedCheese)));

    cheeseService.delete(expectedCheese.getName());

    Mockito.verify(mock, Mockito.times(1)).deleteByNameIgnoreCase("CHEESE");
  }


  @Test(expected = BadRequestException.class)
  public void delete_should_throwBadRequest_whenCheeseDoesntExists() {

    try {
      cheeseService.delete("CHEESE");
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Cheese " + "CHEESE" + " doesn't exist"));
      throw e;
    }
  }
}
