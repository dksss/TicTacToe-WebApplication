package org.s21.tictactoe.web.model;

import java.util.UUID;

public class GameWeb {
  private final UUID id;
  private final BoardWeb board;

  public GameWeb(UUID id, BoardWeb board) {
    this.id = id;
    this.board = board;
  }

  public void makeMove(int y, int x) {
    board.setValue(y, x, BoardWeb.X);
  }

  public UUID getId() {
    return id;
  }

  public BoardWeb getBoard() {
    return board;
  }
}
