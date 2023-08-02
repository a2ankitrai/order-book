package com.bitvavo.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Trade {
  String aggressingOrderId;
  String restingOrderId;
  int price;
  int quantity;

  @Override
  public String toString() {
    return "trade " + aggressingOrderId + "," + restingOrderId + "," + price + "," + quantity;
  }
}
