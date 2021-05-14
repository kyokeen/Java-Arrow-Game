package Model.Strategies;

import Model.IGameBoard;
import Model.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MinMaxAlphaBetaStrategy implements IStrategy {
    private final int maxDepth;
    private final int maxMoves;

    public MinMaxAlphaBetaStrategy(int maxDepth, int maxMoves) {
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
            int val = maxValue(board, 0, 0, 64);
            if(val > max) {
                max = val;
                bestMove = move;
            }
            board.undoMove(move);
        }
        return bestMove;
    }

    private int maxValue(IGameBoard board, int level, int alpha, int beta) {
        if(level == maxDepth) return evaluationFunction(board);
        int max = 0;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = minValue(board, level + 1, alpha, beta);
            if(val >= beta) return val;
            if(val > alpha) alpha = val;
            board.undoMove(move);
        }
        return max;
    }

    private int minValue(IGameBoard board, int level, int alpha, int beta) {
        if(level == maxDepth) return evaluationFunction(board);
        int min = 64;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = maxValue(board, level + 1, alpha, beta);
            if(val < alpha) return val;
            if(val < beta) beta = val;
            board.undoMove(move);
        }
        return min;
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
