package org.rgoussey;

import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day6 {

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(6);
    Room room = new Room(lines);
    Guard startingGuard = room.getStartingGuard();
    room.walkGuard(startingGuard);
    System.out.println("Positions visited: " + startingGuard.getAmountOfPlacesVisited());

    boolean[][] newObstacles = room.findNewObstacles();
    int count = Day6.countObstacles(newObstacles);
    System.out.println("New obstacles: " + count);
  }

  protected record Position(int y, int x) {

    Position(final Position position) {
      this(position.y(), position.x());
    }

  }

  protected static class Guard {

    private byte direction;
    private Position position;
    private final byte[][] history;

    Guard(Guard guard) {
      this.position = new Position(guard.position);
      this.direction = guard.direction;
      this.history = new byte[guard.history.length][guard.history[0].length];
      for (int i = 0; i < guard.history.length; i++) {
        System.arraycopy(guard.history[i], 0, history[i], 0, guard.history[i].length);
      }
    }

    Guard(int depth, int length, final Position startPosition, byte direction) {
      this.position = new Position(startPosition);
      this.direction = direction;
      history = new byte[depth][length];
    }

    private void recordHistory() {
      history[position.y()][position.x()] |= (byte) (0x01
          << direction);
    }

    public boolean hasVisited(Position position, byte direction) {
      return (history[position.y()][position.x()] & ((byte) (0x01
          << direction))) != 0;
    }

    public byte getDirection() {
      return direction;
    }

    public Position getPosition() {
      return position;
    }

    void rotateRight() {
      direction = (byte) ((direction + 1) % 4);
    }

    void moveForward() {
      if (direction == 0) {
        position = new Position(position.y - 1, position.x);
      }
      if (direction == 1) {
        position = new Position(position.y, position.x + 1);
      }
      if (direction == 2) {
        position = new Position(position.y + 1, position.x);
      }
      if (direction == 3) {
        position = new Position(position.y, position.x - 1);
      }
    }

    private Position getPositionOnDirection(int direction) {
      if (direction > 3 || direction < 0) {
        direction = direction % 4;
      }
      return switch (direction) {
        case 0 -> new Position(position.y - 1, position.x);
        case 1 -> new Position(position.y, position.x + 1);
        case 2 -> new Position(position.y + 1, position.x);
        default -> new Position(position.y, position.x - 1);
      };
    }

    Position getPositionInFront() {
      return getPositionOnDirection(direction);
    }

    void drawState(boolean[][] obstacles, Position newObstacle) {
      for (int i = 0; i < history.length; i++) {
        for (int j = 0; j < history[i].length; j++) {
          if (obstacles[i][j]) {
            System.out.print("#");
          } else if (newObstacle.y() == i && newObstacle.x() == j) {
            System.out.print("O");
          } else if (position.y() == i && position.x() == j) {
            switch (direction) {
              case 0 -> System.out.print("^");
              case 1 -> System.out.print(">");
              case 2 -> System.out.print("v");
              case 3 -> System.out.print("<");
            }
          } else if (history[i][j] == 0) {
            System.out.print(".");
          } else {
            boolean hasVisitedVertically =
                (history[i][j] & 0x01) != 0 || (history[i][j] & 0x04) != 0;
            boolean hasVisitedHorizontally =
                (history[i][j] & 0x02) != 0 || (history[i][j] & 0x08) != 0;
            if (hasVisitedVertically && hasVisitedHorizontally) {
              System.out.print("+");
            } else if (hasVisitedVertically) {
              System.out.print("|");
            } else if (hasVisitedHorizontally) {
              System.out.print("-");
            }
          }
        }
        System.out.println();
      }
      System.out.println("========");
    }

    int getAmountOfPlacesVisited() {
      int count = 0;
      for (int i = 0; i < history.length; i++) {
        byte[] bytes = history[i];
        for (int j = 0; j < bytes.length; j++) {
          if (bytes[j] != 0) {
            count++;
          }
        }
      }
      return count;
    }
  }

  enum GuardState {
    WALKING, OUT_OF_ROOM, LOOPING
  }

  protected static class Room {

    boolean[][] obstacles;
    // Axis is flipped, 0 is top, increases going down
    int startX;
    // Axis is flipped, 0 is top, increases going down
    int startY;

    Room(Room room) {
      this.obstacles = new boolean[room.obstacles.length][room.obstacles[0].length];
      for (int i = 0; i < room.obstacles.length; i++) {
        System.arraycopy(room.obstacles[i], 0, obstacles[i], 0, room.obstacles[i].length);
      }
      this.startX = room.startX;
      this.startY = room.startY;
    }

    Room(List<String> lines) {
      int depth = lines.size();
      int length = lines.getFirst().length();

      obstacles = new boolean[depth][length];
      for (int i = 0; i < depth; i++) {
        String line = lines.get(i);
        for (int j = 0; j < line.length(); j++) {
          obstacles[i][j] = line.charAt(j) == '#';
          if (line.charAt(j) == '^') {
            startX = j;
            startY = i;
          }
        }
      }
    }

    Guard getStartingGuard() {
      return new Guard(obstacles.length, obstacles[0].length, new Position(startY, startX),
          (byte) 0);
    }

    void walkGuardOutOfRoom(Guard guard) {
      GuardState guardState;
      do {
        guardState = walkGuard(guard);
      } while (guardState == GuardState.WALKING);
    }

    GuardState walkGuard(Guard guard) {
      if (!isPositionInRoom(guard.getPosition())) {
        return GuardState.OUT_OF_ROOM;
      }
      if (guard.hasVisited(guard.getPosition(), guard.getDirection())) {
        return GuardState.LOOPING;
      }
      guard.recordHistory();
      if (isPositionInRoom(guard.getPositionInFront()) && isObstacle(guard.getPositionInFront())) {
        guard.rotateRight();
      } else {
        guard.moveForward();
      }
      return GuardState.WALKING;
    }

    boolean[][] findNewObstacles() {
      boolean[][] newObstacles = new boolean[obstacles.length][obstacles[0].length];
      GuardState guardState;
      Guard guard = getStartingGuard();
      do {
        guardState = walkGuard(guard);
        Position positionInFront = guard.getPositionInFront();
        if (!isPositionInRoom(positionInFront)) {
          return newObstacles;
        }
        byte reverseDirection = (byte) (guard.getDirection() + 2 % 4);
        boolean guardCameFromThatDirection = guard.hasVisited(positionInFront,
            reverseDirection);
        if (!isObstacle(positionInFront) && !guardCameFromThatDirection) {
          if(isLoopWithNewObstacle(positionInFront, guard)){
            newObstacles[positionInFront.y()][positionInFront.x()] = true;
            guard.drawState(obstacles, positionInFront);
          }
        }
      } while (guardState == GuardState.WALKING);
      return newObstacles;
    }

    private boolean isLoopWithNewObstacle(Position positionInFront, Guard guard) {
      Room newRoom = new Room(this);
      newRoom.obstacles[positionInFront.y()][positionInFront.x()] = true;
      Guard loopGuard = new Guard(guard);
      loopGuard.rotateRight();
      GuardState loopGuardState;
      do {
        loopGuardState = newRoom.walkGuard(loopGuard);
      } while (loopGuardState == GuardState.WALKING);
      return loopGuardState == GuardState.LOOPING;
    }


    private boolean isPositionInRoom(Position position) {
      return position.x >= 0 && position.x < obstacles[0].length && position.y >= 0
          && position.y < obstacles.length;
    }

    boolean isObstacle(Position position) {
      return obstacles[position.y()][position.x()];
    }
  }

  static int countObstacles(boolean[][] obstacles) {
    int count = 0;
    for (int i = 0; i < obstacles.length; i++) {
      for (int j = 0; j < obstacles[i].length; j++) {
        if (obstacles[i][j]) {
          count++;
        }
      }
    }
    return count;
  }
}
