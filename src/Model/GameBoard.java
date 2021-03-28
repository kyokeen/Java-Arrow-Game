package Model;

import java.util.ArrayList;

public class GameBoard implements IGameBoard {
    private final int size;
    private Arrow[][] board;
    private final String[] directions;

    public GameBoard(int size) {
        this.size = size;
        if (size == 4) directions = smallBoardDirections;
        else directions = largeBoardDirections;
        board = new Arrow[size][size];
    }

    public GameBoard(GameBoard gameBoard) {
        this.size = gameBoard.getSize();
        this.directions = gameBoard.directions;
        this.board = new Arrow[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            System.arraycopy(gameBoard.board[i], 0, this.board[i], 0, this.size);
        }
    }

    public boolean makeMove(Move move) {
        if (!isValidMove(move)) return false;
        int row = move.getX();
        int column = move.getY();
        board[row][column] = move.getArrow();
        return true;
    }

    public void undoMove(Move move) {
        int row = move.getX();
        int column = move.getY();
        board[row][column] = null;
    }

    public boolean isValidMove(Move move) {
        int row = move.getX();
        int column = move.getY();
        Arrow arrow = move.getArrow();
        if (null != board[row][column]) return false;
        for (int i = 0; i < size; i++) {

            if (arrow.equals(board[row][i]) && i != column) return false;
            if (arrow.equals(board[i][column]) && i != row) return false;

            int rmi = row - i - 1;
            int cmi = column - i - 1;
            int rpi = row + i + 1;
            int cpi = column + i + 1;

            if (rmi >= 0) {
                if (cpi < size) {
                    if (arrow.equals(board[rmi][cpi])) return false;
                }
                if (cmi >= 0) {
                    if (arrow.equals(board[rmi][cmi])) return false;
                }
            }
            if (rpi < size) {
                if (cpi < size) {
                    if (arrow.equals(board[rpi][cpi])) return false;
                }
                if (cmi >= 0) {
                    if (arrow.equals(board[rpi][cmi])) return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (null != board[i][j]) continue;
                for (int dirIdx = 0; dirIdx < size; dirIdx++) {
                    Move move = new Move(i, j, new Arrow(directions[dirIdx]));
                    if (isValidMove(move)) result.add(move);
                }
            }
        }
        return result;
    }

    public int noValidMoves() {
        return getValidMoves().size();
    }

    @Override
    public void clearBoard() {
        this.board = new Arrow[size][size];
    }

    public int getSize() {
        return size;
    }
}
