package org.ryank.pizza.create.resources.config.ingredient.service;

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
import org.ryank.pizza.create.resources.config.ingredient.repository.IngredientRepository;
import org.ryank.pizza.create.resources.config.ingredient.repository.dataobject.IngredientDO;
import org.ryank.pizza.create.resources.config.ingredient.service.model.Ingredient;

public class IngredientServiceTest {

  private IngredientService ingredientService;
  private IngredientRepository mock;

  @Before
  public void setupMock() {
    mock = Mockito.mock(IngredientRepository.class);
    ingredientService = new IngredientServiceImpl(mock);
  }

  @Test
  public void getAll_should_returnAll_whenThereIsResultsFromRepository() {
    Ingredient expectedIngredient = new Ingredient("INGREDIENT", 1.0);
    Mockito.when(mock.findAll())
        .thenReturn(Collections.singletonList(new IngredientDO(expectedIngredient)));

    List<Ingredient> resultIngredients = ingredientService.get();

    assertThat(resultIngredients.get(0), is(expectedIngredient));
  }

  @Test
  public void getAll_should_returnEmpty_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findAll()).thenReturn(Collections.emptyList());

    List<Ingredient> resultIngredients = ingredientService.get();

    assertThat(resultIngredients.size(), is(0));
  }

  @Test
  public void get_should_returnIngredient_whenResultIsFound() {
    Ingredient expectedIngredient = new Ingredient("INGREDIENT", 1.0);
    Mockito.when(mock.findById(any(String.class)))
        .thenReturn(Optional.of(new IngredientDO(expectedIngredient)));

    Optional<Ingredient> resultIngredient = ingredientService.get(expectedIngredient.getName());

    assertThat(resultIngredient.get(), is(expectedIngredient));
  }

  @Test
  public void get_should_returnEmptyOptional_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findById(any(String.class))).thenReturn(Optional.empty());

    Optional<Ingredient> resultIngredient = ingredientService.get("INGREDIENT");

    assertThat(resultIngredient.isPresent(), is(false));
  }

  @Test
  public void create_should_returnCreatedObject_whenValidInput() {
    Ingredient expectedIngredient = new Ingredient("INGREDIENT", 1.0);
    Mockito.when(mock.save(any(IngredientDO.class)))
        .thenReturn(new IngredientDO(expectedIngredient));

    Ingredient resultIngredient = ingredientService.create(expectedIngredient);

    assertThat(resultIngredient, is(expectedIngredient));
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenIngredientAlreadyExists() {
    Ingredient existingIngredient = new Ingredient("INGREDIENT", 1.0);
    Mockito.when(mock.findById(any(String.class)))
        .thenReturn(Optional.of(new IngredientDO(existingIngredient)));

    try {
      ingredientService.create(existingIngredient);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Ingredient " + existingIngredient.getName() + " already exists"));
      throw e;
    }
  }

  @Test
  public void update_should_returnUpdatedObject_whenValidInput() {
    Ingredient expectedIngredient = new Ingredient("INGREDIENT", 1.0);
    Mockito.when(mock.save(any(IngredientDO.class)))
        .thenReturn(new IngredientDO(expectedIngredient));

    Ingredient resultIngredient = ingredientService.update(expectedIngredient);

    assertThat(resultIngredient, is(expectedIngredient));
  }

  @Test(expected = BadRequestException.class)
  public void update_should_throwBadRequest_whenDoesntExist() {
    Ingredient expectedIngredient = new Ingredient("INGREDIENT", 1.0);
    try {
      ingredientService.update(expectedIngredient);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Ingredient " + "INGREDIENT" + " doesn't exist"));
      throw e;
    }
  }
  
  @Test
  public void delete_should_returnDeletedObject_whenExists() {
    Ingredient expectedIngredient = new Ingredient("INGREDIENT", 1.0);
    Mockito.when(mock.deleteByName(any(String.class)))
        .thenReturn(Optional.of(new IngredientDO(expectedIngredient)));

    Ingredient resultIngredient = ingredientService.delete(expectedIngredient.getName());

    assertThat(resultIngredient, is(expectedIngredient));
  }


  @Test(expected = BadRequestException.class)
  public void delete_should_throwBadRequest_whenIngredientDoesntExists() {

    try {
      ingredientService.delete("INGREDIENT");
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Ingredient " + "INGREDIENT" + " doesn't exist"));
      throw e;
    }
  }
}
