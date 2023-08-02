package com.bitvavo.core;

import com.bitvavo.domain.Order;
import com.bitvavo.formatting.OrderBookFormatter;
import com.bitvavo.hashing.Hasher;
import java.util.List;
import lombok.Getter;

public class Exchange {
  private final OrderBook orderBook;
  private final Hasher hasher;

  @Getter
  private final List<Trade> trades;

  public Exchange(OrderBook orderBook, Hasher hasher) {
    this.orderBook = orderBook;
    this.hasher = hasher;
    this.trades = new java.util.ArrayList<>();
  }


  public void processOrder(Order order) {
    List<Trade> newTrades = orderBook.processOrder(order);
    trades.addAll(newTrades);
  }

  public void printOrderBook() {
    OrderBookFormatter orderBookFormatter = new OrderBookFormatter(orderBook, hasher);
    String formattedOrderBook = orderBookFormatter.getFormattedOrderBookWithHash();
    System.out.println(formattedOrderBook);
  }

  public void printTrades() {
     this.getTrades().forEach(System.out::println);
  }

  public void printTradesAndOrderBook() {
    printTrades();
    printOrderBook();
  }
}
