package Model;


import Model.Strategies.IStrategy;
import Model.Strategies.RandomStrategy;

public class SystemPlayer extends Player{
    private IStrategy strategy;

    public SystemPlayer(String color) {
        super(color);
        this.strategy = new RandomStrategy();
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public Move makeMove(IGameBoard board) {
        return strategy.makeMove(board);
    }

}
