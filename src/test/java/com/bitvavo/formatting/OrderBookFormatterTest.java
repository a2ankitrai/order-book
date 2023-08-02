package com.bitvavo.formatting;

import static com.bitvavo.util.TestUtil.buildTestOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bitvavo.core.OrderBook;
import com.bitvavo.domain.Order;
import com.bitvavo.domain.OrderType;
import com.bitvavo.hashing.Hasher;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderBookFormatterTest {

  @Mock
  private OrderBook mockOrderBook;

  @Mock
  private Hasher mockHasher;

  private OrderBookFormatter orderBookFormatter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    orderBookFormatter = new OrderBookFormatter(mockOrderBook, mockHasher);
  }

  @Test
  void testFormatOrderBook() {
    List<Order> buyOrders = Arrays.asList(
        buildTestOrder("1", OrderType.BUY, 100, 500),
        buildTestOrder("2", OrderType.BUY, 200, 600)
    );
    List<Order> sellOrders = Arrays.asList(
        buildTestOrder("3", OrderType.SELL, 300, 700),
        buildTestOrder("4", OrderType.SELL, 400, 800)
    );

    when(mockOrderBook.getBuyOrders()).thenReturn(buyOrders);
    when(mockOrderBook.getSellOrders()).thenReturn(sellOrders);

    String formattedOrderBook = orderBookFormatter.formatOrderBook();

    String expectedOrderBook =
        """
                  100    500 |    700       300
                  200    600 |    800       400
            """;
    assertEquals(expectedOrderBook, formattedOrderBook);
  }

  @Test
  void testGetFormattedOrderBookWithHash() {

    String expectedHash = "6cd3556deb0da54bca060b4c39479839";

    when(mockOrderBook.getBuyOrders()).thenReturn(Arrays.asList(
        buildTestOrder("1", OrderType.BUY, 100, 500),
        buildTestOrder("2", OrderType.BUY, 200, 600)
    ));
    when(mockOrderBook.getSellOrders()).thenReturn(Arrays.asList(
        buildTestOrder("3", OrderType.SELL, 300, 700),
        buildTestOrder("4", OrderType.SELL, 400, 800)
    ));
    when(mockHasher.getHash(any())).thenReturn(expectedHash);

    String formattedOrderBookWithHash = orderBookFormatter.getFormattedOrderBookWithHash();

    String expectedFormattedOrderBookWithHash =
        """
              100    500 |    700       300
              200    600 |    800       400
        """ + "\n" +
            "Hash: " + expectedHash;
    assertEquals(expectedFormattedOrderBookWithHash, formattedOrderBookWithHash);
  }
}
