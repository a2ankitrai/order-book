package com.bitvavo.core;

import static com.bitvavo.util.TestUtil.buildTestOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bitvavo.domain.Order;
import com.bitvavo.domain.OrderType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriorityQueueOrderBookTest {

  private OrderBook orderBook;

  @BeforeEach
  void setUp() {
    orderBook = new PriorityQueueOrderBook();
  }

  @Test
  void testProcessOrder_BuyOrder() {
    Order buyOrder1 = buildTestOrder("1", OrderType.BUY, 1000, 99);
    Order buyOrder2 = buildTestOrder("2", OrderType.BUY, 500, 100);

    List<Trade> trades1 = orderBook.processOrder(buyOrder1);
    List<Trade> trades2 = orderBook.processOrder(buyOrder2);

    assertTrue(trades1.isEmpty(), "No trades should occur with buyOrder1");
    assertTrue(trades2.isEmpty(), "No trades should occur with buyOrder2");

    List<Order> buyOrders = orderBook.getBuyOrders();
    assertEquals(2, buyOrders.size(), "There should be 2 buy orders in the order book");
    assertEquals(buyOrder2, buyOrders.get(0), "buyOrder2 should be at the top of buy orders");
    assertEquals(buyOrder1, buyOrders.get(1), "buyOrder1 should be below buyOrder2");
  }

  @Test
  void testProcessOrder_SellOrder() {
    Order sellOrder1 = buildTestOrder("3", OrderType.SELL, 2000, 101);
    Order sellOrder2 = buildTestOrder("4", OrderType.SELL, 800, 100);

    List<Trade> trades1 = orderBook.processOrder(sellOrder1);
    List<Trade> trades2 = orderBook.processOrder(sellOrder2);

    assertTrue(trades1.isEmpty(), "No trades should occur with sellOrder1");
    assertTrue(trades2.isEmpty(), "No trades should occur with sellOrder2");

    List<Order> sellOrders = orderBook.getSellOrders();
    assertEquals(2, sellOrders.size(), "There should be 2 sell orders in the order book");
    assertEquals(sellOrder2, sellOrders.get(0), "sellOrder2 should be at the top of sell orders");
    assertEquals(sellOrder1, sellOrders.get(1), "sellOrder1 should be below sellOrder2");
  }

  @Test
  void testProcessOrder_Trades() {
    Order buyOrder = buildTestOrder("1", OrderType.BUY, 2000, 100);
    Order sellOrder = buildTestOrder("2", OrderType.SELL, 1000, 99);

    List<Trade> trades = orderBook.processOrder(buyOrder);
    assertTrue(trades.isEmpty(), "No trades should occur with buyOrder");

    trades = orderBook.processOrder(sellOrder);
    assertEquals(1, trades.size(), "There should be 1 trade with sellOrder");
    Trade trade = trades.get(0);
    assertEquals("2", trade.getAggressingOrderId(), "Aggressing order ID should match");
    assertEquals("1", trade.getRestingOrderId(), "Resting order ID should match");
    assertEquals(99, trade.getPrice(), "Price should match");
    assertEquals(1000, trade.getQuantity(), "Quantity should match");

    List<Order> buyOrders = orderBook.getBuyOrders();
    assertFalse(buyOrders.isEmpty(), "buy orders should be left after trade");

    List<Order> sellOrders = orderBook.getSellOrders();
    assertTrue(sellOrders.isEmpty(), "No sell orders should be left after trade");
  }

}
