package org.s21.tictactoe.datasource.mapper;

import org.s21.tictactoe.datasource.model.GameData;
import org.s21.tictactoe.domain.model.Game;

public class DataGameMapper {

  private final DataBoardMapper dataBoardMapper = new DataBoardMapper();

  public GameData toDatasource(Game game) {
    var id = game.getId();
    var board = dataBoardMapper.toDatasource(game.getBoard());

    return new GameData(id, board);
  }

  public Game toDomain(GameData gameData) {
    var id = gameData.getId();
    var board = dataBoardMapper.toDomain(gameData.getBoardData());

    return new Game(id, board);
  }
}
