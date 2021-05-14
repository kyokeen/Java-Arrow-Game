package View;

import Controller.Controller;
import Model.Arrow;
import Model.Move;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class BoardObserver implements Observer {
    private final Controller controller;

    public BoardObserver(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (null == arg) {
            for (Node node : controller.board.getChildren()) {
                if (node instanceof ImageView) {
                    ((ImageView) node).setImage(null);
                }
            }
            return;
        }
        Move move = (Move) arg;
        Arrow arrow = move.getArrow();

        String imageName = "images/" + arrow.toString() + ".png";
        Image img = new Image(imageName);

        for (Node node : controller.board.getChildren()) {
            if (node instanceof ImageView) {
                if (GridPane.getRowIndex(node) == move.getX() && GridPane.getColumnIndex(node) == move.getY()) {
                    if(null == ((ImageView)node).getImage()) {
                        ((ImageView) node).setImage(img);
                    } else {
                        ((ImageView) node).setImage(null);
                    }
                    break;
                }
            }
        }
    }
}
