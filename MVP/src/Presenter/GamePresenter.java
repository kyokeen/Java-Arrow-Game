package Presenter;

import Model.*;
import Model.Strategies.MinMaxStrategy;
import View.IView;

import static java.lang.Thread.sleep;

public class GamePresenter {
    private final IView view;
    private final IGameModel model;

    public GamePresenter(IView view) {
        this.view = view;
        model = new Game(new SystemPlayer("r"), new Player("g"), new GameBoard(8));
        model.getSystemPlayer().setStrategy(new MinMaxStrategy(2, 10));
    }

    public void userRegisterMove(String direction, int row, int column) {
        boolean valid = model.makeUserMove(new Move(row, column, new Arrow(model.getUserPlayer().getColor(), direction)));
        if(!valid) {
            view.signalInvalidMove();
            return;
        }
        view.placeArrow(model.getUserPlayer().getColor(), direction, row, column);
        if(model.isEndgame()) {
            view.signalEndgame("User");
            return;
        }

        Move move = model.getSystemMove();
        view.placeArrow(model.getSystemPlayer().getColor(), move.getArrow().getDirection(), move.getX(), move.getY());
        if(model.isEndgame()) view.signalEndgame("Computer");
    }

    public void setPlayerColor(String color) {
        if(model.getUserPlayer().getColor().equals(color)) return;
        model.getSystemPlayer().setColor(model.getUserPlayer().getColor());
        model.getUserPlayer().setColor(color);
        model.changePlayerColor(model.getUserPlayer(), color);
    }

    public void changeLevel(int boardSize) { //TODO: listeners for choice boxes
        model.changeBoardSize(boardSize);
    }

    public void undoMove() {
        Move sysMove = model.undo();
        Move usrMove = model.undo();
        if(null != sysMove) view.removeArrow(sysMove.getX(), sysMove.getY());
        if(null != usrMove) view.removeArrow(usrMove.getX(), usrMove.getY());
    }

    public void clearBoard() {
        model.clearBoard();
    }

}
