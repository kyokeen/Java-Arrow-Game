package Model;

public interface IGameModel {
    boolean makeUserMove(Move move);
    void changePlayerColor(Player player, String color);
    void changeBoardSize(int size);
    Move getSystemMove();
    Move undo();
    Player getUserPlayer();
    SystemPlayer getSystemPlayer();
    boolean isEndgame();
    void clearBoard();
}
