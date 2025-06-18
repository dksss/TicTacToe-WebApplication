package org.s21.tictactoe.domain.model;

public class Board {
  public static final int HEIGHT = 3;
  public static final int WIDTH = 3;

  public static final int EMPTY = 0;
  public static final int X = 1;
  public static final int O = 2;

  private final int[][] field;

  public Board() {
    this.field = new int[HEIGHT][WIDTH];
  }

  public Board(int[][] field) {
    this.field = field;
  }

  public int[][] getField() {
    return field;
  }

  public void setValue(Position pos, int value) {
    int y = pos.getY();
    int x = pos.getX();
    
    field[y][x] = value;
  }

  public int getValue(int y, int x) {
    return field[y][x];
  }
}
