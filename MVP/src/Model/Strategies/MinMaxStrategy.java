package Model.Strategies;

import Model.IGameBoard;
import Model.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MinMaxStrategy implements IStrategy {
    private final int maxDepth;
    private final int maxMoves;

    public MinMaxStrategy(int maxDepth, int maxMoves) {
        this.maxDepth = maxDepth;
        this.maxMoves = maxMoves;
    }

    private int evaluationFunction(IGameBoard board) {
        return board.noValidMoves();
    }

    @Override
    public Move makeMove(IGameBoard board) {
        Move bestMove = null;
        int max = 0;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = maxValue(board, 0);
            if(val > max) {
                max = val;
                bestMove = move;
            }
            board.undoMove(move);
        }
        return bestMove;
    }

    private int minValue(IGameBoard board, int level) {
        if(level == maxDepth) return evaluationFunction(board);
        int min = 64;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = maxValue(board, level + 1);
            if(val < min) min = val;
            board.undoMove(move);
        }
        return min;
    }

    private int maxValue(IGameBoard board, int level) {
        if(level == maxDepth) return evaluationFunction(board);
        int max = 0;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = minValue(board, level + 1);
            if(val > max) max = val;
            board.undoMove(move);
        }
        return max;
    }

    private ArrayList<Move> getNElements(ArrayList<Move> source, int n) {
        Random rand = new Random();
        ArrayList<Move> destination = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int randomIndex = rand.nextInt(source.size());
            destination.add(source.get(randomIndex));
            source.remove(randomIndex);
        }
        return destination;
    }
}
