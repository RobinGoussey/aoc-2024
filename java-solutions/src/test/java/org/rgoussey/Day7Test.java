package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rgoussey.Day7.parseLines;
import static org.rgoussey.Day7.sumSolvableEquations;

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
    List<EquationPermutationHolder> equationPermutationHolders = parseLines(lines, List.of(new Day7.Addition(), new Day7.Multiplication()));
    long count = sumSolvableEquations(equationPermutationHolders);
    assertThat(count).isEqualTo(3749);
   equationPermutationHolders = parseLines(lines, List.of(new Day7.Addition(), new Day7.Multiplication(), new Day7.Concatenate()));
    count = sumSolvableEquations(equationPermutationHolders);
    assertThat(count).isEqualTo(11387);

  }

}
