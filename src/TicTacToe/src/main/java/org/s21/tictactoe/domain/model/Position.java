package org.s21.tictactoe.domain.model;

public class Position {
  private final int y;
  private final int x;

  public Position(int y, int x) {
    this.y = y;
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }
}
