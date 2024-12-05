package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day3Test {

  @Test
  void getSumOfMultiplications() {
    String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    long result = Day3.getSumOfMultiplications(List.of(input));
    assertThat(result).isEqualTo(161);
    long result2 = Day3.getSumOfMultiplicationsWithDoAndDonts(List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"));
    assertThat(result2).isEqualTo(48);
  }
}
