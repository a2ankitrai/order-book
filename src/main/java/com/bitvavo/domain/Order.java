package com.bitvavo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class Order {
    String orderId;
    OrderType type;
    int quantity;
    int price;
    long timestamp;
}
