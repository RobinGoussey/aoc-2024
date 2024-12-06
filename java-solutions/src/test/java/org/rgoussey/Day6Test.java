package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day6.Maze;
import org.rgoussey.shared.FileUtils;

public class Day6Test {

  @Test
  void test() {
    List<String> lines = FileUtils.getLines(6);
    Maze maze = new Maze(lines);
    maze.draw();
    assertThat(maze.countPositionsVisited()).isEqualTo(41);
  }


}
