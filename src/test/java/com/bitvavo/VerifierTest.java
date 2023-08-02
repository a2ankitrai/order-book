package com.bitvavo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerifierTest {

  private ByteArrayOutputStream outputStream;

  @BeforeEach
  void setUp() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void testVerifierWithSampleInput() {
    String input = """
        10000,B,98,25500
        10005,S,105,20000
        10001,S,100,500
        10002,S,100,10000
        10003,B,99,50000
        10004,S,103,100
        """;

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    Verifier.main(new String[]{});

    String expectedOutput =
        """
               50,000     99 |    100       500
               25,500     98 |    100    10,000
                           |    103       100
                           |    105    20,000
                        
            Hash: 87d87ccfed698f1a962c1320fc0896f0
            """;
    assertEquals(expectedOutput, outputStream.toString());
  }
}
