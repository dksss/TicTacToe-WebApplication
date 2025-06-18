package org.s21.tictactoe.domain.service;

import org.s21.tictactoe.datasource.repository.DataRepository;
import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.Position;

import java.util.UUID;

public class GameServiceImpl implements GameService {

  private final DataRepository dataRepository;

  public GameServiceImpl(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  @Override
  public void makeAiMove(Game game) {
    if (!isGameOver(game)) {
      var move = getBestMove(game);
      game.makeMove(move, Board.O);
    }

    dataRepository.save(game);
  }

  @Override
  public boolean isValid(Game game) {
    var gameData = dataRepository.getById(game.getId());

    var sourceField = gameData.getBoard().getField();
    var currentField = game.getBoard().getField();

    return isValidField(sourceField, currentField) && !isMissingMove(sourceField, currentField);
  }

  @Override
  public boolean isGameOver(Game game) {
    var board = game.getBoard();
    return isWinning(board, Board.X) || isWinning(board, Board.O) || isBoardFilled(board);
  }

  @Override
  public UUID startGame() {
    Game game = new Game();
    dataRepository.save(game);

    return game.getId();
  }

  @Override
  public Game getGameById(UUID id) {
    return dataRepository.getById(id);
  }

  private boolean isValidField(int[][] source, int[][] current) {
    for (int i = 0; i < Board.HEIGHT; i++) {
      for (int j = 0; j < Board.WIDTH; j++) {
        if (source[i][j] != Board.EMPTY && source[i][j] != current[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean isWinning(Board board, int playerValue) {
    var field = board.getField();
    return (checkLines(field, playerValue) || checkColumns(field, playerValue)
            || checkDiagonalLines(field, playerValue));
  }

  private boolean checkLines(int[][] field, int value) {
    boolean check = false;
    for (int i = 0; i < Board.HEIGHT; i++) {
      if (field[i][0] == value && field[i][0] == field[i][1] && field[i][1] == field[i][2]) {
        check = true;
        break;
      }
    }

    return check;
  }

  private boolean checkColumns(int[][] field, int value) {
    boolean check = false;
    for (int j = 0; j < Board.WIDTH; j++) {
      if (field[0][j] == value && field[0][j] == field[1][j] && field[1][j] == field[2][j]) {
        check = true;
        break;
      }
    }

    return check;
  }

  private boolean checkDiagonalLines(int[][] field, int value) {
    return ((field[0][0] == value && field[0][0] == field[1][1] && field[1][1] == field[2][2]) ||
            (field[0][2] == value && field[0][2] == field[1][1] && field[1][1] == field[2][0]));
  }

  private boolean isBoardFilled(Board board) {
    var field = board.getField();
    for (int i = 0; i < Board.HEIGHT; i++) {
      for (int j = 0; j < Board.WIDTH; j++) {
        if (field[i][j] == Board.EMPTY) {
          return false;
        }
      }
    }

    return true;
  }

  private Position getBestMove(Game game) {
    Position bestMove = null;
    int bestScore = Integer.MIN_VALUE;

    var board = game.getBoard();
    for (int i = 0; i < Board.HEIGHT; ++i) {
      for (int j = 0; j < Board.WIDTH; ++j) {
        if (board.getValue(i, j) == Board.EMPTY) {
          Position pos = new Position(i, j);
          board.setValue(pos, Board.O);
          int score = minimax(board, false);
          board.setValue(pos, Board.EMPTY);

          if (score > bestScore) {
            bestScore = score;
            bestMove = pos;
          }
        }
      }
    }

    return bestMove;
  }

  private int minimax(Board board, boolean isAiTurn) {
    int score = evaluate(board);
    if (Math.abs(score) == 10 || isBoardFilled(board)) {
      return score;
    }

    if (isAiTurn) {
      int highestScore = Integer.MIN_VALUE;
      for (int i = 0; i < Board.HEIGHT; i++) {
        for (int j = 0; j < Board.WIDTH; j++) {
          if (board.getValue(i, j) == Board.EMPTY) {
            Position pos = new Position(i, j);
            board.setValue(pos, Board.O);
            highestScore = Math.max(highestScore, minimax(board, false));
            board.setValue(pos, Board.EMPTY);
          }
        }
      }
      return highestScore;
    } else {
      int lowestScore = Integer.MAX_VALUE;
      for (int i = 0; i < Board.HEIGHT; i++) {
        for (int j = 0; j < Board.WIDTH; j++) {
          if (board.getValue(i, j) == Board.EMPTY) {
            Position pos = new Position(i, j);
            board.setValue(pos, Board.X);
            lowestScore = Math.min(lowestScore, minimax(board, true));
            board.setValue(pos, Board.EMPTY);
          }
        }
      }
      return lowestScore;
    }
  }

  private int evaluate(Board board) {
    int score = 0;

    if (isWinning(board, Board.X)) {
      score = -10;
    } else if (isWinning(board, Board.O)) {
      score = 10;
    }

    return score;
  }

  private int countEmptyCells(int[][] field) {
    int count = 0;

    for (int i = 0; i < Board.HEIGHT; i++) {
      for (int j = 0; j < Board.WIDTH; j++) {
        if (field[i][j] == Board.EMPTY) {
          count++;
        }
      }
    }

    return count;
  }

  private boolean isMissingMove(int[][] source, int[][] current) {
    int srcEmptyCells = countEmptyCells(source);
    int currentEmptyCells = countEmptyCells(current);

    return (srcEmptyCells == currentEmptyCells);
  }
}
