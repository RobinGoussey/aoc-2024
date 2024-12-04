package org.rgoussey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day2 {

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(2);
//    List<Report> reports = lines.stream()
//        .map((String line) -> Report.parseLine(line, 0)).toList();
//    long safeCount = reports.stream().filter(Report::isSafe).count();
//    System.out.println("Part 1 " + safeCount);
    Collection<Report> reports = lines.stream()
        .map((String line) -> Report.parseLine(line, 1)).toList();
    long safeCount = reports.stream().filter(Report::isSafe).count();
    System.out.println("Part 2 " + safeCount);

  }

  protected record Report(List<Delta> data, boolean isSafe) {

    public static Report parseLine(String line, int mistakesAllowed) {
      List<Integer> data = new ArrayList<>(
          Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList());
      List<Delta> deltas = new ArrayList<>();
      for (int i = 0; i < data.size() - 1; i++) {
        Delta delta = new Delta(data.get(i), data.get(i + 1));
        deltas.add(delta);
      }
      boolean isSafe = true;
      boolean firstOneIsIncreasing=deltas.getFirst().isSafe()?deltas.getFirst().isIncreasing():deltas.get(2).isIncreasing();
      boolean directionIsConstant = true;
      for (Delta delta : deltas) {
        if(!delta.isSafe()){
          if (mistakesAllowed <= 0) {
            isSafe = false;
            break;
          }
          mistakesAllowed--;
        } else {
          directionIsConstant &= delta.isIncreasing()==firstOneIsIncreasing;
        }
      }
      return new Report(deltas, isSafe && directionIsConstant);
    }

  }

  record Delta(int first, int second) {

    public boolean isIncreasing() {
      return first <= second;
    }

    public boolean isSafe() {
      int delta = Math.abs(first - second);
      return 1 <= delta && delta <= 3;
    }
  }
}

