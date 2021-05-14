package Model;

public class Move {
    private final int x;
    private final int y;
    private final Arrow arrow;

    public Move(int x, int y, Arrow arrow) {
        this.x = x;
        this.y = y;
        this.arrow = arrow;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Arrow getArrow() {
        return arrow;
    }
}
