package com.bitvavo;

import com.bitvavo.core.Exchange;
import com.bitvavo.core.OrderBook;
import com.bitvavo.core.PriorityQueueOrderBook;
import com.bitvavo.domain.Order;
import com.bitvavo.domain.OrderType;
import com.bitvavo.hashing.Hasher;
import com.bitvavo.hashing.Md5Hasher;
import java.util.Scanner;

public class Verifier {

  public static void main(String[] args) {

    OrderBook orderBook = new PriorityQueueOrderBook();
    Hasher hasher = new Md5Hasher();
    Exchange exchange = new Exchange(orderBook, hasher);

    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] orderDetails = line.split(",");

      String orderId = orderDetails[0];
      OrderType type = orderDetails[1].equals("B") ? OrderType.BUY : OrderType.SELL;
      int price = Integer.parseInt(orderDetails[2]);
      int quantity = Integer.parseInt(orderDetails[3]);

      Order order = new Order(orderId, type, quantity, price, System.currentTimeMillis());
      exchange.processOrder(order);
    }
    exchange.printTradesAndOrderBook();
  }
}
