package com.bitvavo.hashing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bitvavo.hashing.Hasher;
import com.bitvavo.hashing.Md5Hasher;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.junit.jupiter.api.Test;

public class Md5HasherTest {

  @Test
  void testGetHash() {
    String message = "Hello, world!";
    String expectedHash = "6cd3556deb0da54bca060b4c39479839";

    MessageDigest mockMessageDigest = mock(MessageDigest.class);
    when(mockMessageDigest.digest(any())).thenReturn(expectedHash.getBytes(StandardCharsets.UTF_8));

    Hasher hasher = new Md5Hasher();
    String actualHash = hasher.getHash(message);
    assertEquals(expectedHash, actualHash);
  }
}
