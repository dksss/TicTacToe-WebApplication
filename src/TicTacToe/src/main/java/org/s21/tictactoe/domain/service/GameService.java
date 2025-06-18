package org.s21.tictactoe.domain.service;

import org.s21.tictactoe.domain.model.Game;

import java.util.UUID;

public interface GameService {
  void makeAiMove(Game game);

  boolean isValid(Game game);

  boolean isGameOver(Game game);

  UUID startGame();

  Game getGameById(UUID id);
}
