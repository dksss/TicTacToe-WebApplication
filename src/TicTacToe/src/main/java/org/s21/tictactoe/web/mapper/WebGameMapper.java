package org.s21.tictactoe.web.mapper;

import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.web.model.GameWeb;

public class WebGameMapper {
  private final WebBoardMapper webBoardMapper = new WebBoardMapper();

  public GameWeb toWeb(Game game) {
    var id = game.getId();
    var board = webBoardMapper.toWeb(game.getBoard());

    return new GameWeb(id, board);
  }

  public Game toDomain(GameWeb gameWeb) {
    var id = gameWeb.getId();
    var board = webBoardMapper.toDomain(gameWeb.getBoard());

    return new Game(id, board);
  }
}
