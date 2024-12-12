package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rgoussey.Day7.parseLines;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day7.Equation;
import org.rgoussey.Day7.EquationPermutationHolder;
import org.rgoussey.shared.FileUtils;

class Day7Test {

  @Test
  void test() {
    List<String> lines = FileUtils.getLines(7);
    List<EquationPermutationHolder> equationPermutationHolders = parseLines(lines);
    long count = equationPermutationHolders.stream().filter(EquationPermutationHolder::canBeSolved)
        .map(EquationPermutationHolder::desiredResult)
        .mapToInt(Integer::intValue).sum();
    assertThat(count).isEqualTo(3749);
  }

}
