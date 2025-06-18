package org.s21.tictactoe.web.model;

public class BoardWeb {
  public static final String EMPTY = " ";
  public static final String X = "X";
  public static final String O = "O";

  public static final int HEIGHT = 3;
  public static final int WIDTH = 3;

  private final String[][] field;

  public BoardWeb(String[][] field) {
    this.field = field;
  }

  public String[][] getField() {
    return field;
  }

  public void setValue(int y, int x, String value) {
    field[y][x] = value;
  }
}
