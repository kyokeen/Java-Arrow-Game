package Model;

import java.util.ArrayList;

public interface IGameBoard {
    String[] smallBoardDirections = {"N", "S", "E", "W"};
    String[] largeBoardDirections = {"N", "S", "E", "W", "NE", "NW", "SE", "SW"};

    int getSize();
    boolean makeMove(Move move);
    void undoMove(Move move);
    boolean isValidMove(Move move);
    int noValidMoves();
    ArrayList<Move> getValidMoves();
    void clearBoard();
}
