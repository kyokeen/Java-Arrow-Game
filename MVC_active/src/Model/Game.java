package Model;

import View.BoardObserver;

import java.util.Observable;
import java.util.Stack;

public class Game extends Observable implements IGameModel {
    private final SystemPlayer systemPlayer;
    private final Player player;
    private IGameBoard board;
    private final Stack<Move> moveStack;
    private BoardObserver boardObserver;

    public Game(SystemPlayer systemPlayer, Player player, IGameBoard board) {
        this.systemPlayer = systemPlayer;
        this.player = player;
        this.board = board;
        this.moveStack = new Stack<>();
    }

    public boolean makeUserMove(Move move) {
        if (this.board.makeMove(move)) {
            moveStack.push(move);
            boardObserver.update(this, move);
            return true;
        }
        return false;
    }

    @Override
    public void changePlayerColor(Player player, String color) {
        if (!player.getColor().equals(color)) {
            systemPlayer.setColor(player.getColor());
            player.setColor(color);
        }
    }

    @Override
    public Move getSystemMove() {
        Move move = systemPlayer.makeMove(new GameBoard((GameBoard)(this.board)));
        if (null != move) {
            move.updateColor(systemPlayer.getColor());
            moveStack.push(move);
            board.makeMove(move);
            boardObserver.update(this, move);
            return move;
        }
        return null;
    }

    public Move undo() {
        if(moveStack.isEmpty()) return null;
        Move move = moveStack.pop();
        this.board.undoMove(move);
        boardObserver.update(this, move);
        return move;
    }

    public void setBoardObserver(BoardObserver observer) {
        this.boardObserver = observer;
    }

    @Override
    public Player getUserPlayer() {
        return this.player;
    }

    @Override
    public SystemPlayer getSystemPlayer() {
        return this.systemPlayer;
    }

    @Override
    public boolean isEndgame() {
        return this.board.noValidMoves() == 0;
    }

    @Override
    public void clearBoard() {
        this.board.clearBoard();
        moveStack.clear();
        boardObserver.update(this, null);
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size) return;
        this.board = new GameBoard(size);
    }

    public Move getHint() {
        Move move = board.getHint();
        move.updateColor(getUserPlayer().getColor());
        return move;
    }
}
