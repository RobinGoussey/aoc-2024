package org.rgoussey.shared;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FileUtils {

  public static List<String> getLines(int day) {
    String fileName = "day" + day + "/file.txt";
    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
        FileUtils.class.getClassLoader().getResourceAsStream(fileName))) {
      return new BufferedReader(new InputStreamReader(bufferedInputStream)).lines().toList();
    } catch (IOException e) {
      throw new RuntimeException("Error while reading file %s".formatted(fileName),e);
    }
  }

}
