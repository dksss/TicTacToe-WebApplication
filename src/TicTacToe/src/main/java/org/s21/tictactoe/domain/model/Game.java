package org.s21.tictactoe.domain.model;

import java.util.UUID;

public class Game {
  private final UUID id;
  private final Board board;

  public Game() {
    this.id = UUID.randomUUID();
    this.board = new Board();
  }

  public Game(UUID id, Board board) {
    this.id = id;
    this.board = board;
  }

  public void makeMove(Position pos, int value) {
    board.setValue(pos, value);
  }

  public UUID getId() {
    return id;
  }

  public Board getBoard() {
    return board;
  }
}
