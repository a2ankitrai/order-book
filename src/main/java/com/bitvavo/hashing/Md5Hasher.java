package com.bitvavo.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Hasher implements Hasher{

  @Override
  public String getHash(String message) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hashBytes = md.digest(message.getBytes(StandardCharsets.UTF_8));

      StringBuilder sb = new StringBuilder();
      for (byte hashByte : hashBytes) {
        sb.append(String.format("%02x", hashByte));
      }

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
