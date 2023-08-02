package com.bitvavo.formatting;

import com.bitvavo.core.OrderBook;
import com.bitvavo.domain.Order;
import com.bitvavo.hashing.Hasher;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderBookFormatter {
  private final OrderBook orderBook;
  private final Hasher hasher;

  public String formatOrderBook() {
    StringBuilder sb = new StringBuilder();
    List<Order> buyOrdersList = orderBook.getBuyOrders();
    List<Order> sellOrdersList = orderBook.getSellOrders();

    for (int i = 0; i < Math.max(buyOrdersList.size(), sellOrdersList.size()); i++) {
      if (i < buyOrdersList.size()) {
        sb.append(String.format(Locale.US, "%,9d %,6d |", buyOrdersList.get(i).getQuantity(), buyOrdersList.get(i).getPrice()));
      } else {
        sb.append("               |");
      }

      if (i < sellOrdersList.size()) {
        sb.append(String.format(Locale.US, " %6d %,9d", sellOrdersList.get(i).getPrice(), sellOrdersList.get(i).getQuantity()));
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  public String getFormattedOrderBookWithHash() {
    String orderBook = formatOrderBook();
    String hash = hasher.getHash(orderBook);
    return orderBook + "\nHash: " + hash;
  }

}
