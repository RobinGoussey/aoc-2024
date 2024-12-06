package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day5.PageOrderValidator;
import org.rgoussey.shared.FileUtils;

public class Day5Test {

  @Test
  void test() {
    List<String> lines = FileUtils.getLines(5);
    PageOrderValidator pageOrderValidator = new PageOrderValidator(lines);
    pageOrderValidator.getUpdatesInOrder().forEach(array -> {
      for (int i : array) {
        System.out.print(i + " ");
      }
      System.out.println();
    });
    assertThat(
        pageOrderValidator.getSumOfMiddleNumbers(pageOrderValidator.getUpdatesInOrder())).isEqualTo(
        143);
  }

  @Test
  void testPart2() {
    List<String> lines = FileUtils.getLines(5);
    PageOrderValidator pageOrderValidator = new PageOrderValidator(lines);
    List<int[]> updatesNotInOrder = pageOrderValidator.getUpdatesNotInOrder();
    updatesNotInOrder.forEach(array -> {
      for (int i : array) {
        System.out.print(i + " ");
      }
      System.out.println();
    });
    assertThat(pageOrderValidator.getSumOfMiddleNumbers(
        pageOrderValidator.sort(updatesNotInOrder))).isEqualTo(123);
  }

}
