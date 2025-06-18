package org.s21.tictactoe.datasource.model;

import java.util.UUID;

public class GameData {
  private final UUID id;
  private final BoardData boardData;

  public GameData(UUID id, BoardData boardData) {
    this.id = id;
    this.boardData = boardData;
  }

  public UUID getId() {
    return id;
  }

  public BoardData getBoardData() {
    return boardData;
  }
}
