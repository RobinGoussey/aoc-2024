package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day6.Maze;
import org.rgoussey.shared.FileUtils;

class Day6Test {

  @Test
  void test() {
    List<String> lines = FileUtils.getLines(6);
    Maze maze = new Maze(lines);
    maze.draw(maze.path,null);
    assertThat(maze.countPositionsVisited()).isEqualTo(41);
    System.out.println("========");
    maze.drawPathForNewObstacles();
    assertThat(maze.countNewObstacles()).isEqualTo(6);

  }


}
