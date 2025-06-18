package org.s21.tictactoe.web.controller;

import org.s21.tictactoe.domain.service.GameService;
import org.s21.tictactoe.web.mapper.WebGameMapper;
import org.s21.tictactoe.web.model.BoardWeb;
import org.s21.tictactoe.web.model.GameWeb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GameController {

  private final WebGameMapper webGameMapper = new WebGameMapper();
  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping
  public String start() {
    return "start";
  }

  @PostMapping
  public String newGame() {
    var gameId = gameService.startGame();

    return "redirect:/game/" + gameId;
  }

  @GetMapping("/{UUID}")
  public String game(@PathVariable UUID UUID,
                     Model model) {
    var game = gameService.getGameById(UUID);

    if (game == null) {
      model.addAttribute("message", "Invalid game ID");
    } else {
      model.addAttribute("UUID", UUID);
      addInfoToPage(model, webGameMapper.toWeb(game));

      if (gameService.isGameOver(game)) {
        model.addAttribute("message", "Game over");
      }
    }

    return "game";
  }

  @PostMapping("/{UUID}")
  public String gameMove(@PathVariable UUID UUID,
                         @RequestParam int ceil,
                         @RequestParam Map<String, String> fieldState,
                         Model model) {
    GameWeb currentGame = getInfoFromPage(UUID, fieldState);

    var existingGame = gameService.getGameById(UUID);
    if (existingGame == null) {
      model.addAttribute("message", "Invalid game ID: " + UUID);
      return "game";
    }

    if (gameService.isGameOver(existingGame)) {
      model.addAttribute("message", "Game over");
      addInfoToPage(model, webGameMapper.toWeb(existingGame));
      return "game";
    }

    int y = ceil / 3;
    int x = ceil % 3;
    currentGame.makeMove(y, x);
    if (!gameService.isValid(webGameMapper.toDomain(currentGame))) {
      model.addAttribute("message",
              "Invalid move: row = " + (y + 1) + ", column = " + (x + 1));
      addInfoToPage(model, webGameMapper.toWeb(existingGame));
      return "game";
    }

    gameService.makeAiMove(webGameMapper.toDomain(currentGame));

    return "redirect:/game/" + currentGame.getId();
  }

  private void addInfoToPage(Model page, GameWeb game) {
    page.addAttribute("UUID", game.getId());

    var fieldInfo = game.getBoard().getField();

    int ceilIndex = 0;
    for (int i = 0; i < fieldInfo.length; i++)
      for (int j = 0; j < fieldInfo[i].length; j++) {
        String attributeName = "ceil" + ceilIndex++;
        page.addAttribute(attributeName, fieldInfo[i][j]);
      }
  }

  private GameWeb getInfoFromPage(UUID id, Map<String, String> info) {
    String[][] tmpField = new String[BoardWeb.HEIGHT][BoardWeb.WIDTH];

    int ceilIndex = 0;
    for (int i = 0; i < tmpField.length; ++i) {
      for (int j = 0; j < tmpField[i].length; ++j) {
        String key = "ceil" + ceilIndex++;
        String value = info.get(key);
        tmpField[i][j] = value;
      }
    }

    BoardWeb boardWeb = new BoardWeb(tmpField);

    return new GameWeb(id, boardWeb);
  }
}
