package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day2.Report;
import org.rgoussey.shared.FileUtils;

class Day2Test {

  @Test
  void test() {
    List<Report> reports = FileUtils.getLines(2).stream()
        .map((String line) -> Report.parseLine(line,0)).toList();
    long safeCount = reports.stream().filter(Report::isSafe).count();
    assertThat(safeCount).isEqualTo(2);
    reports = FileUtils.getLines(2).stream()
        .map((String line) -> Report.parseLine(line,1)).toList();
    safeCount = reports.stream().filter(Report::isSafe).count();
    assertThat(safeCount).isEqualTo(4);
  }

}
