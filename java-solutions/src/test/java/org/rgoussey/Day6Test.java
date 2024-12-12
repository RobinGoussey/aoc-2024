package org.rgoussey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.rgoussey.Day6.Guard;
import org.rgoussey.Day6.Room;
import org.rgoussey.shared.FileUtils;

class Day6Test {

  @Test
  void test() {
    List<String> lines = FileUtils.getLines(6);
    Room room = new Room(lines);
    Guard startingGuard = room.getStartingGuard();
    room.walkGuardOutOfRoom(startingGuard);
    assertThat(startingGuard.getAmountOfPlacesVisited()).isEqualTo(41);
    System.out.println("========");
    boolean[][] newObstacles = room.findNewObstacles();
    int count = Day6.countObstacles(newObstacles);
    assertThat(count).isEqualTo(6);

  }


}
