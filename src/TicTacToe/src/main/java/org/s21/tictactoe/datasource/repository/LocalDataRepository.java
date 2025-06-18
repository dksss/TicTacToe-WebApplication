package org.s21.tictactoe.datasource.repository;

import org.s21.tictactoe.datasource.mapper.DataGameMapper;
import org.s21.tictactoe.datasource.storage.LocalStorage;
import org.s21.tictactoe.domain.model.Game;

import java.util.UUID;

public class LocalDataRepository implements DataRepository {

  private final LocalStorage storage;
  private final DataGameMapper mapper;

  public LocalDataRepository(LocalStorage storage) {
    this.storage = storage;
    this.mapper = new DataGameMapper();
  }

  @Override
  public void save(Game game) {
    var data = mapper.toDatasource(game);
    storage.save(data);
  }

  @Override
  public Game getById(UUID id) {
    if (!storage.contains(id)) {
      return null;
    }

    var data = storage.getById(id);
    return mapper.toDomain(data);
  }
}
