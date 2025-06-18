package org.s21.tictactoe.datasource.mapper;

import org.s21.tictactoe.datasource.model.BoardData;
import org.s21.tictactoe.domain.model.Board;

public class DataBoardMapper {
  public BoardData toDatasource(Board board) {
    var fieldDomain = board.getField();
    return new BoardData(deepCopy(fieldDomain));
  }

  public Board toDomain(BoardData boardData) {
    var fieldData = boardData.getField();
    return new Board(deepCopy(fieldData));
  }

  private int[][] deepCopy(int[][] src) {
    int[][] dest = new int[Board.HEIGHT][Board.WIDTH];

    for (int i = 0; i < Board.HEIGHT; i++) {
      for (int j = 0; j < Board.WIDTH; j++) {
        dest[i][j] = src[i][j];
      }
    }

    return dest;
  }
}
