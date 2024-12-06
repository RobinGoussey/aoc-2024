package org.rgoussey;

import java.util.ArrayList;
import java.util.List;
import org.rgoussey.shared.FileUtils;

public class Day6 {

  public static void main(String[] args) {
    List<String> lines = FileUtils.getLines(6);
    Maze maze = new Maze(lines);
    maze.draw();
    System.out.println("Positions visited: "+maze.countPositionsVisited());
  }

  protected static class Maze {

    boolean[][] obstacles;
    boolean[][] path;
    // Axis is flipped, 0 is top, increases going down
    int startX;
    // Axis is flipped, 0 is top, increases going down
    int startY;
    // 0 = up, 1 = right, 2 = down, 3 = left
    int direction = 0;


    Maze(List<String> lines) {
      obstacles = new boolean[lines.size()][lines.getFirst().length()];
      for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i);
        for (int j = 0; j < line.length(); j++) {
          obstacles[i][j] = line.charAt(j) == '#';
          if (line.charAt(j) == '^') {
            startX = j;
            startY = i;
          }
        }
      }
      path = figureOutPathWithTurnRight();
    }

    boolean[][] figureOutPathWithTurnRight() {
      int length = obstacles[0].length;
      int depth = obstacles.length;
      boolean[][] path = new boolean[depth][length];
      int x = startX;
      int y = startY;
      while (x >= 0 && x < length && y >= 0 && y < depth) {
        path[y][x] = true;
        if (direction == 0) {
          if (y == 0 || !obstacles[y - 1][x]) {
            y--;
          } else {
            x++;
            direction = 1;
          }
        } else if (direction == 1) {
          if (x == length - 1 || !obstacles[y][x + 1]) {
            x++;
          } else {
            y++;
            direction = 2;
          }
        } else if (direction == 2) {
          if (y == depth - 1 || !obstacles[y + 1][x]) {
            y++;
          } else {
            x--;
            direction = 3;
          }
        } else if (direction == 3) {
          if (x == 0 || !obstacles[y][x - 1]) {
            x--;
          } else {
            y--;
            direction = 0;
          }
        }
      }
      return path;

    }

    void draw() {
      for (int i = 0; i < obstacles.length; i++) {
        for (int j = 0; j < obstacles[i].length; j++) {
          if (obstacles[i][j]) {
            System.out.print("#");
          } else if (path[i][j]) {
            System.out.print("X");
          } else {
            System.out.print(".");
          }
        }
        System.out.println();
      }
    }

    int countPositionsVisited(){
      int count = 0;
      for (int i = 0; i < path.length; i++) {
        for (int j = 0; j < path[i].length; j++) {
          if (path[i][j]) {
            count++;
          }
        }
      }
      return count;
    }


  }
}
