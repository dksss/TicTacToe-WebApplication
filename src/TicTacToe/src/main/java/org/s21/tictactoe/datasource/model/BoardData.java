package org.s21.tictactoe.datasource.model;

public class BoardData {
  private final int[][] field;

  public BoardData(int[][] field) {
    this.field = field;
  }

  public int[][] getField() {
    return field;
  }
}
