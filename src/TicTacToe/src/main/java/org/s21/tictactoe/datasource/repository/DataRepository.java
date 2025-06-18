package org.s21.tictactoe.datasource.repository;

import org.s21.tictactoe.domain.model.Game;

import java.util.UUID;

public interface DataRepository {
  void save(Game game);

  Game getById(UUID id);
}
