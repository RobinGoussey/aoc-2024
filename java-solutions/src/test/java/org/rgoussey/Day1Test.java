package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.rgoussey.Day1.getSumOfDelta;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.shared.FileUtils;

class Day1Test {

  @Test
  void test(){
    List<String> lines = FileUtils.getLines(1);
    long sumOfDelta = getSumOfDelta(lines);
    assertThat(sumOfDelta).isEqualTo(11);
    long similarity = Day1.getSimilarity(lines);
    assertThat(similarity).isEqualTo(31);
  }
}
