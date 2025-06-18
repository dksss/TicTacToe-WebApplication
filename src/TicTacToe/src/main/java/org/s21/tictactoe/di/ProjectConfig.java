package org.s21.tictactoe.di;

import org.s21.tictactoe.datasource.repository.DataRepository;
import org.s21.tictactoe.datasource.repository.LocalDataRepository;
import org.s21.tictactoe.datasource.storage.LocalStorage;
import org.s21.tictactoe.domain.service.GameService;
import org.s21.tictactoe.domain.service.GameServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class ProjectConfig {

  @Bean
  public LocalStorage localStorage() {
    return new LocalStorage();
  }

  @Bean
  @SessionScope
  public DataRepository dataRepository(LocalStorage localStorage) {
    return new LocalDataRepository(localStorage);
  }

  @Bean
  @SessionScope
  public GameService gameService(DataRepository dataRepository) {
    return new GameServiceImpl(dataRepository);
  }
}
