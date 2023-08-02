package com.bitvavo.util;

import com.bitvavo.domain.Order;
import com.bitvavo.domain.OrderType;

public class TestUtil {
  public static Order buildTestOrder(String orderId, OrderType type, int quantity, int price) {
    return Order.builder()
      .orderId(orderId)
      .type(type)
      .quantity(quantity)
      .price(price)
      .timestamp(System.currentTimeMillis())
      .build();
  }

}
