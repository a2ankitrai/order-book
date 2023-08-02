package com.bitvavo.core;

import com.bitvavo.domain.Order;
import java.util.List;

public interface OrderBook {

  List<Trade> processOrder(Order order);

  List<Order> getBuyOrders();

  List<Order> getSellOrders();
}
