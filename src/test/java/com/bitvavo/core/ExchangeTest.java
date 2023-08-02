package com.bitvavo.core;

import static com.bitvavo.util.TestUtil.buildTestOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitvavo.domain.Order;
import com.bitvavo.domain.OrderType;
import com.bitvavo.hashing.Hasher;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ExchangeTest {

  private Exchange exchange;
  private OrderBook orderBook;

  @BeforeEach
  void setUp() {
    orderBook = Mockito.mock(OrderBook.class);
    Hasher hasher = Mockito.mock(Hasher.class);
    exchange = new Exchange(orderBook, hasher);
  }

  @Test
  void processOrder_ShouldAddTradesToList() {

    Order order1 = buildTestOrder("1", OrderType.BUY, 1000, 99);
    Order order2 = buildTestOrder("2", OrderType.SELL, 2000, 101);

    exchange.processOrder(order1);
    exchange.processOrder(order2);

    List<Trade> expectedTrades = List.of(
        new Trade(order1.getOrderId(), order2.getOrderId(), 99, 1000));

    when(orderBook.processOrder(order2)).thenReturn(expectedTrades);
    exchange.processOrder(order1);
    exchange.processOrder(order2);

    assertEquals(expectedTrades.size(), exchange.getTrades().size());
    assertEquals(expectedTrades.get(0), exchange.getTrades().get(0));
  }
}
