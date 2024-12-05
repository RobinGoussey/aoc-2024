package org.rgoussey;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.rgoussey.shared.FileUtils;

public class Day3 {

  static final Pattern mulPattern = java.util.regex.Pattern.compile("mul\\((\\d+),(\\d+)\\)");

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(3);
    long sumOfMultiplications = getSumOfMultiplications(lines);
    System.out.println("Part 1 " + sumOfMultiplications);
    long sumOfMultiplicationsWithDoAndDonts = getSumOfMultiplicationsWithDoAndDonts(lines);
    System.out.println("Part 2 " + sumOfMultiplicationsWithDoAndDonts);

  }

  static long getSumOfMultiplications(List<String> lines) {
    return lines.stream()
        .mapToLong(line -> {
          var matcher = mulPattern.matcher(line);
          long sum = 0;
          while (matcher.find()) {
            int first = Integer.parseInt(matcher.group(1));
            int second = Integer.parseInt(matcher.group(2));
            long delta = (long) first * second;
            sum += delta;
          }
          return sum;
        })
        .sum();
  }

  static long getSumOfMultiplicationsWithDoAndDonts(List<String> lines) {
    String text = String.join("\n", lines);
    String[] split = text.split("don't\\(\\)");
    List<String> enabledText = new ArrayList<>();
    enabledText.add(split[0]);
    for (int i = 1; i < split.length; i++) {
      String[] split2 = split[i].split("do\\(\\)",2);
      if(split2.length==2){
        enabledText.add(split2[1]);
      }
    }
    String enabled = String.join("", enabledText);
    return getSumOfMultiplications(List.of(enabled));
  }


}

