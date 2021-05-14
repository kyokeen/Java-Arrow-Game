package Model.Strategies;

import Model.IGameBoard;
import Model.Move;

public interface IStrategy {
    Move makeMove(IGameBoard board);
}
