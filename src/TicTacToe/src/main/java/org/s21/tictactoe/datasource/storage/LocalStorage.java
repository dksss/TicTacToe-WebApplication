package org.s21.tictactoe.datasource.storage;

import org.s21.tictactoe.datasource.model.GameData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LocalStorage {
  private final ConcurrentHashMap<UUID, GameData> storage = new ConcurrentHashMap<>();

  public void save(GameData gameData) {
    storage.put(gameData.getId(), gameData);
  }

  public GameData getById(UUID id) {
    return storage.get(id);
  }

  public boolean contains(UUID id) {
    return storage.containsKey(id);
  }
}
