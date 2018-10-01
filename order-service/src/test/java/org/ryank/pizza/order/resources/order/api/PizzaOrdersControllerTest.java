package org.ryank.pizza.order.resources.order.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ryank.pizza.order.resources.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest()
public class PizzaOrdersControllerTest {

  @Autowired
  PizzaOrdersController controller;

  @MockBean
  OrderService orderService;

  @Test
  public void getAll_should_returnAllOrders() {

  }

}
