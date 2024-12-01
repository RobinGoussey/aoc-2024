package org.rgoussey;

import java.util.ArrayList;
import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day1 {

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(1);
    long sumOfDelta = getSumOfDelta(lines);
    System.out.println("Part 1 " + sumOfDelta);
    long similarity = getSimilarity(lines);
    System.out.println("Part 2 " + similarity);
  }

  /**
   * This time, you'll need to figure out exactly how often each number from the left list appears
   * in the right list. Calculate a total similarity score by adding up each number in the left list
   * after multiplying it by the number of times that number appears in the right list.
   * I noticed that for each time the left number x was present; you had to add the frequency (y) it was present in the right list.
   * So if x was present n times. It became n * x* y.
   *
   * @param lines The unparsed list of lines.
   * @return The similarity score.
   */
  public static long getSimilarity(List<String> lines) {
    List<List<Integer>> numbers = parseLines(lines);
    List<Integer> frequencyTableLeft = getFrequencyTable(numbers.get(0));
    List<Integer> frequencyTableRight = getFrequencyTable(numbers.get(1));
    long similarity = 0;
    for (int i = 0; i < frequencyTableLeft.size(); i++) {
      similarity += (long) frequencyTableLeft.get(i) * i * frequencyTableRight.get(i);
    }
    return similarity;
  }

  private static List<Integer> getFrequencyTable(List<Integer> numbers) {
    List<Integer> frequencyTable = new ArrayList<>();
    numbers.forEach(number -> {
      boolean frequencyTableTooSmall = frequencyTable.size() < number + 1;
      if (frequencyTableTooSmall) {
        int numberOfItemsTooSmall = number - frequencyTable.size() + 1;
        for (int i = 0; i < numberOfItemsTooSmall; i++) {
          frequencyTable.add(0);
        }
      }
      frequencyTable.set(number, frequencyTable.get(number) + 1);
    });
    return frequencyTable;
  }

  public static long getSumOfDelta(List<String> lines) {
    List<List<Integer>> numbers = parseLines(lines);
    numbers.forEach(list -> list.sort(Integer::compareTo));
    long sumOfDelta = 0;
    for (int i = 0; i < numbers.get(0).size(); i++) {
      sumOfDelta += Math.abs(numbers.get(0).get(i) - numbers.get(1).get(i));
    }
    return sumOfDelta;
  }

  private static List<List<Integer>> parseLines(List<String> lines) {
    List<List<Integer>> numbers = new ArrayList<>();

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      String[] split = line.split("\\s+");
      if (numbers.size() <= split.length) {
        for (int i1 = 0; i1 < split.length; i1++) {
          numbers.add(new ArrayList<>(lines.size()));
        }
      }
      for (int j = 0; j < split.length; j++) {
        numbers.get(j).add(Integer.parseInt(split[j]));
      }
    }
    return numbers;
  }
}
