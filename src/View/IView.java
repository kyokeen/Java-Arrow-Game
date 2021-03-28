package View;

public interface IView {
    void placeArrow(String color, String direction, int row, int column);
    void removeArrow(int row, int column);
    void signalEndgame(String winner);
    void signalInvalidMove();
}
