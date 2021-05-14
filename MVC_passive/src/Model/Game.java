package Model;

import java.util.Stack;

public class Game implements IGameModel {
    private final SystemPlayer systemPlayer;
    private final Player player;
    private IGameBoard board;
    private final Stack<Move> moveStack;

    public Game(SystemPlayer systemPlayer, Player player, IGameBoard board) {
        this.systemPlayer = systemPlayer;
        this.player = player;
        this.board = board;
        this.moveStack = new Stack<>();
    }

    public boolean makeUserMove(Move move) {
        if (this.board.makeMove(move)) {
            moveStack.push(move);
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
            moveStack.push(move);
            board.makeMove(move);
            return move;
        }
        return null;
    }

    public Move undo() {
        if(moveStack.isEmpty()) return null;
        Move move = moveStack.pop();
        this.board.undoMove(move);
        return move;
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
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size) return;
        this.board = new GameBoard(size);
    }

}
