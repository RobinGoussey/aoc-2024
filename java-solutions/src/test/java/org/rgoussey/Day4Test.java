package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day4.WordFinder;
import org.rgoussey.shared.FileUtils;

class Day4Test {

  @Test
  void test() {
    List<String> lines = FileUtils.getLines(4);
    WordFinder wordFinder = new WordFinder(lines);
    long xmasCount = wordFinder.countValid("XMAS".toCharArray());
    assertThat(xmasCount).isEqualTo(18);
    long xCount = wordFinder.xmasCountFinder();
    assertThat(xCount).isEqualTo(9);

  }

}
