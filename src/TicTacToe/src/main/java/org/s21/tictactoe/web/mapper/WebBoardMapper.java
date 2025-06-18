package org.s21.tictactoe.web.mapper;

import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.web.model.BoardWeb;

public class WebBoardMapper {
  public BoardWeb toWeb(Board source) {
    String[][] fieldWeb = new String[Board.HEIGHT][Board.WIDTH];

    var fieldDomain = source.getField();
    for (int i = 0; i < Board.HEIGHT; i++) {
      for (int j = 0; j < Board.WIDTH; j++) {
        fieldWeb[i][j] = getStringValue(fieldDomain[i][j]);
      }
    }

    return new BoardWeb(fieldWeb);
  }

  public Board toDomain(BoardWeb boardWeb) {
    int[][] fieldDomain = new int[Board.HEIGHT][Board.WIDTH];

    var fieldWeb = boardWeb.getField();
    for (int i = 0; i < Board.HEIGHT; i++) {
      for (int j = 0; j < Board.WIDTH; j++) {
        fieldDomain[i][j] = getIntValue(fieldWeb[i][j]);
      }
    }

    return new Board(fieldDomain);
  }

  private String getStringValue(int value) {
    return switch(value) {
      case 1 -> BoardWeb.X;
      case 2 -> BoardWeb.O;
      default -> BoardWeb.EMPTY;
    };
  }

  private int getIntValue(String value) {
    return switch (value) {
      case BoardWeb.X -> 1;
      case BoardWeb.O -> 2;
      default -> 0;
    };
  }
}

