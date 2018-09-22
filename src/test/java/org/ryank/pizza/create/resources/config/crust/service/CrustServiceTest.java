package org.ryank.pizza.create.resources.config.crust.service;

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
import org.ryank.pizza.create.resources.config.crust.repository.CrustRepository;
import org.ryank.pizza.create.resources.config.crust.repository.dataobject.CrustDO;
import org.ryank.pizza.create.resources.config.crust.service.model.Crust;

public class CrustServiceTest {

  private CrustService crustService;
  private CrustRepository mock;

  @Before
  public void setupMock() {
    mock = Mockito.mock(CrustRepository.class);
    crustService = new CrustServiceImpl(mock);
  }

  @Test
  public void getAll_should_returnAll_whenThereIsResultsFromRepository() {
    Crust expectedCrust = new Crust("CRUST", 1.0);
    Mockito.when(mock.findAll())
        .thenReturn(Collections.singletonList(new CrustDO(expectedCrust)));

    List<Crust> resultCrusts = crustService.get();

    assertThat(resultCrusts.get(0), is(expectedCrust));
  }

  @Test
  public void getAll_should_returnEmpty_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findAll()).thenReturn(Collections.emptyList());

    List<Crust> resultCrusts = crustService.get();

    assertThat(resultCrusts.size(), is(0));
  }

  @Test
  public void get_should_returnCrust_whenResultIsFound() {
    Crust expectedCrust = new Crust("CRUST", 1.0);
    Mockito.when(mock.findById(any(String.class)))
        .thenReturn(Optional.of(new CrustDO(expectedCrust)));

    Optional<Crust> resultCrust = crustService.get(expectedCrust.getName());

    assertThat(resultCrust.get(), is(expectedCrust));
  }

  @Test
  public void get_should_returnEmptyOptional_whenThereIsNoResultsFromRepository() {
    Mockito.when(mock.findById(any(String.class))).thenReturn(Optional.empty());

    Optional<Crust> resultCrust = crustService.get("CRUST");

    assertThat(resultCrust.isPresent(), is(false));
  }

  @Test
  public void create_should_returnCreatedObject_whenValidInput() {
    Crust expectedCrust = new Crust("CRUST", 1.0);
    Mockito.when(mock.save(any(CrustDO.class)))
        .thenReturn(new CrustDO(expectedCrust));

    Crust resultCrust = crustService.create(expectedCrust);

    assertThat(resultCrust, is(expectedCrust));
  }

  @Test(expected = BadRequestException.class)
  public void create_should_throwBadRequest_whenCrustAlreadyExists() {
    Crust existingCrust = new Crust("CRUST", 1.0);
    Mockito.when(mock.findById(any(String.class)))
        .thenReturn(Optional.of(new CrustDO(existingCrust)));

    try {
      crustService.create(existingCrust);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Crust " + existingCrust.getName() + " already exists"));
      throw e;
    }
  }

  @Test
  public void update_should_returnUpdatedObject_whenValidInput() {
    Crust expectedCrust = new Crust("CRUST", 1.0);
    Mockito.when(mock.save(any(CrustDO.class)))
        .thenReturn(new CrustDO(expectedCrust));
    Mockito.when(mock.findById(expectedCrust.getName()))
        .thenReturn(Optional.of(new CrustDO(expectedCrust)));

    Crust resultCrust = crustService.update(expectedCrust);

    assertThat(resultCrust, is(expectedCrust));
  }

  @Test(expected = BadRequestException.class)
  public void update_should_throwBadRequest_whenDoesntExist() {
    Crust expectedCrust = new Crust("CRUST", 1.0);
    try {
      crustService.update(expectedCrust);
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Crust " + "CRUST" + " doesn't exist"));
      throw e;
    }
  }
  
  @Test
  public void delete_should_returnDeletedObject_whenExists() {
    Crust expectedCrust = new Crust("CRUST", 1.0);
    Mockito.when(mock.deleteByName(any(String.class)))
        .thenReturn(Optional.of(new CrustDO(expectedCrust)));

    Crust resultCrust = crustService.delete(expectedCrust.getName());

    assertThat(resultCrust, is(expectedCrust));
  }


  @Test(expected = BadRequestException.class)
  public void delete_should_throwBadRequest_whenCrustDoesntExists() {

    try {
      crustService.delete("CRUST");
    } catch (BadRequestException e) {
      assertThat(e.getMessage(), is("Crust " + "CRUST" + " doesn't exist"));
      throw e;
    }
  }
}
