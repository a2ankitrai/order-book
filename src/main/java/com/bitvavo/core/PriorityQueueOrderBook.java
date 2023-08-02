package com.bitvavo.core;

import com.bitvavo.domain.Order;
import com.bitvavo.domain.OrderType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueueOrderBook implements OrderBook {

  private final PriorityQueue<Order> buyOrders;
  private final PriorityQueue<Order> sellOrders;

  public PriorityQueueOrderBook() {
    buyOrders = new PriorityQueue<>(
        Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getTimestamp));
    sellOrders = new PriorityQueue<>(
        Comparator.comparing(Order::getPrice).thenComparing(Order::getTimestamp));
  }

  @Override
  public List<Trade> processOrder(Order order) {
    addOrder(order);
    return matchOrders(order);
  }

  private void addOrder(Order order) {
    if (order.getType() == OrderType.BUY) {
      buyOrders.offer(order);
    } else if (order.getType() == OrderType.SELL) {
      sellOrders.offer(order);
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  private List<Trade> matchOrders(Order newOrder) {
    List<Trade> trades = new ArrayList<>();

    while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
      Order buyOrder = buyOrders.peek();
      Order sellOrder = sellOrders.peek();

      if (buyOrder.getPrice() >= sellOrder.getPrice()) {
        int matchedPrice = sellOrder.getPrice();

        int quantityToMatch = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
        buyOrder.setQuantity(buyOrder.getQuantity() - quantityToMatch);
        sellOrder.setQuantity(sellOrder.getQuantity() - quantityToMatch);

        if (buyOrder.getQuantity() == 0) {
          buyOrders.poll();
        }
        if (sellOrder.getQuantity() == 0) {
          sellOrders.poll();
        }

        Order aggressingOrder = buyOrder.equals(newOrder) ? buyOrder : sellOrder;
        Order restingOrder = buyOrder.equals(newOrder) ? sellOrder : buyOrder;

        Trade trade = new Trade(aggressingOrder.getOrderId(), restingOrder.getOrderId(),
            matchedPrice, quantityToMatch);
        trades.add(trade);
      } else {
        break;
      }
    }

    return trades;
  }


  @Override
  public List<Order> getBuyOrders() {
    return this.transformPriorityQueueToList(buyOrders);
  }

  @Override
  public List<Order> getSellOrders() {
    return this.transformPriorityQueueToList(sellOrders);
  }

  private List<Order> transformPriorityQueueToList(PriorityQueue<Order> priorityQueue) {
    List<Order> list = new ArrayList<>();
    while (!priorityQueue.isEmpty()) {
      list.add(priorityQueue.poll());
    }
    priorityQueue.addAll(list);
    return list;
  }
}
