package Model.Strategies;

import Model.IGameBoard;
import Model.Move;

import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy implements IStrategy {

    @Override
    public Move makeMove(IGameBoard board) {
        Random random = new Random();
        ArrayList<Move> validMoves = board.getValidMoves();
        int idx = random.nextInt(validMoves.size());
        return validMoves.get(idx);
    }
}
