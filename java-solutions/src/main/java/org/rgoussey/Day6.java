package org.rgoussey;

import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day6 {

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(6);
    Maze maze = new Maze(lines);
    maze.draw(maze.path, null);
    System.out.println("Positions visited: " + maze.countPositionsVisited());
    maze.drawPathForNewObstacles();
  }

  protected record Position(int y, int x) {

  }

  protected static class Guard {

    private byte direction;
    private Position position;

    Guard(int y, int x, byte direction) {
      this.position = new Position(y, x);
      this.direction = direction;
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

    Position getPositionOnLeft() {
      return getPositionOnDirection(direction - 1);
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
  }

  protected static class Maze {

    boolean[][] obstacles;
    boolean[][] path;
    boolean[][] pathForNewObstacles;
    // Axis is flipped, 0 is top, increases going down
    int startX;
    // Axis is flipped, 0 is top, increases going down
    int startY;


    Maze(List<String> lines) {
      int depth = lines.size();
      int length = lines.getFirst().length();

      obstacles = new boolean[depth][length];
      path = new boolean[depth][length];
      pathForNewObstacles = new boolean[depth][length];
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
      figureOutPathWithTurnRight();
    }

    void figureOutPathWithTurnRight() {
      Guard guard = new Guard(startY, startX, (byte) 0);
      while (isPositionInRoom(guard.position)) {
        path[guard.position.y][guard.position.x] = true;
        Position positionInFront = guard.getPositionInFront();
        if (!isPositionInRoom(positionInFront)) {
          break; // We reached the end
        } else if (isObstacle(positionInFront)) {
          guard.rotateRight();
        } else {
          if (obstacleWouldCauseLoop(guard)) {
            pathForNewObstacles[guard.position.y][guard.position.x] = true;
          }
          guard.moveForward();
        }
      }
    }

    private boolean obstacleWouldCauseLoop(Guard guard) {
      Guard guardCopy = new Guard(guard.getPosition().y(), guard.getPosition().x(),
          (byte) (guard.getDirection() + 0x01));
      byte[][] directionsCrossedOnPath = new byte[path.length][path[0].length];
      for (int i = 0; i < directionsCrossedOnPath.length; i++) {
        for (int j = 0; j < directionsCrossedOnPath[0].length; j++) {
          directionsCrossedOnPath[i][j] = 0;
        }
      }
      while (isPositionInRoom(guardCopy.position)) {
        Position positionInFront = guardCopy.getPositionInFront();
        if ((directionsCrossedOnPath[guardCopy.position.y][guardCopy.position.x] & ((byte) (0x01
            << guardCopy.getDirection()))) != 0) {
          return true;
        }
        if (!isPositionInRoom(positionInFront)) {
          return false;
        } else if (isObstacle(positionInFront)) {
          guardCopy.rotateRight();
        } else {
          //Bitshift logic, a 1 will be set at the direction we came from
          directionsCrossedOnPath[guardCopy.position.y][guardCopy.position.x] |= (byte) (0x01
              << guardCopy.getDirection());
          guardCopy.moveForward();
        }
      }
      return false;
    }

    private boolean isPositionInRoom(Position position) {
      return position.x >= 0 && position.x < obstacles[0].length && position.y >= 0
          && position.y < obstacles.length;
    }

    boolean isObstacle(Position position) {
      return obstacles[position.y()][position.x()];
    }

    void draw(Position position) {
      draw(position.y(), position.x());
    }

    //Debug function
    void draw(int y, int x) {
      for (int i = 0; i < obstacles.length; i++) {
        for (int j = 0; j < obstacles[i].length; j++) {
          if (i == y && j == x) {
            System.out.print(".");
          } else if (obstacles[i][j]) {
            System.out.print("#");
          } else if (path[i][j]) {
            System.out.print("X");
          } else {
            System.out.print(" ");
          }
        }
        System.out.println();
      }
      System.out.println("==============================");
    }

    void draw(boolean[][] pathToDraw, Position position) {
      for (int i = 0; i < obstacles.length; i++) {
        for (int j = 0; j < obstacles[i].length; j++) {
          if (position != null && i == position.y() && j == position.x()) {
            System.out.print("1");
          } else if (obstacles[i][j]) {
            System.out.print("#");
          } else if (pathToDraw[i][j]) {
            System.out.print("X");
          } else {
            System.out.print(".");
          }
        }
        System.out.println();
      }
      System.out.println("==============================");
    }

    void drawPathForNewObstacles() {
      for (int i = 0; i < obstacles.length; i++) {
        for (int j = 0; j < obstacles[i].length; j++) {
          if (obstacles[i][j]) {
            System.out.print("#");
          } else if (pathForNewObstacles[i][j]) {
            System.out.print("O");
          } else {
            System.out.print(".");
          }
        }
        System.out.println();
      }
    }

    int countPositionsVisited() {
      return countTrueInNestedArray(path);
    }

    int countNewObstacles() {
      return countTrueInNestedArray(pathForNewObstacles);
    }

    private int countTrueInNestedArray(boolean[][] pathForNewObstacles) {
      int count = 0;
      for (int i = 0; i < pathForNewObstacles.length; i++) {
        for (int j = 0; j < pathForNewObstacles[i].length; j++) {
          if (pathForNewObstacles[i][j]) {
            count++;
          }
        }
      }
      return count;
    }


  }
}
