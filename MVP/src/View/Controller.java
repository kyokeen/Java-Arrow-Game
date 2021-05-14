package View;

import Presenter.GamePresenter;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements IView{ //TODO: rename controller class, e.g. GameView
    public ChoiceBox<String> colorSelectChoiceBox;
    public ChoiceBox<String> levelSelectChoiceBox;
    public Button startGameButton;
    public GridPane largeBoard;
    public GridPane smallBoard;
    public Button restartButton;
    @FXML
    public GridPane board;
    public Button northEastButton;
    public Button southEastButton;
    public Button southWestButton;
    public Button northWestButton;
    public Button northButton;
    public Button southButton;
    public Button eastButton;
    public Button westButton;
    public Button undoButton;
    protected ImageView selectedImage;
    public String selectedDirection = "N";
    private GamePresenter presenter;

    @FXML
    private void initialize() {
        presenter = new GamePresenter(this);
        setButtonImages("g");
        loadChoiceBoxes();
    }

    public void clickedBoard(MouseEvent mouseEvent) {
        EventTarget source = mouseEvent.getTarget();
        if(!(source instanceof ImageView)) return;
        selectedImage = (ImageView)source;
        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);
        presenter.userRegisterMove(selectedDirection, row, col);
    }

    public void clickedArrowButton(MouseEvent mouseEvent) {
        Button button = (Button)mouseEvent.getSource();
        selectedDirection = button.getText();
    }

    public void clickedStartGame(MouseEvent mouseEvent) {
        clearBoard();
        String selectedColor = colorSelectChoiceBox.getValue();
        String color = selectedColor.toLowerCase().charAt(0) + "";
        presenter.setPlayerColor(color);
        setButtonImages(color);
        if(null != board) board.setVisible(false);
        String selectedBoard = levelSelectChoiceBox.getValue();
        if(selectedBoard.equals("4x4")) {
            board = smallBoard;
            presenter.changeLevel(4);
            adjustInterButtons(false);
        } else {
            board = largeBoard;
            presenter.changeLevel(8);
            adjustInterButtons(true);
        }
        board.setVisible(true);
    }

    private void loadChoiceBoxes() {
        colorSelectChoiceBox.setValue("Green");
        colorSelectChoiceBox.getItems().add("Red");
        colorSelectChoiceBox.getItems().add("Green");
        levelSelectChoiceBox.setValue("8x8");
        levelSelectChoiceBox.getItems().add("4x4");
        levelSelectChoiceBox.getItems().add("8x8");
    }

    public void clickedRestartButton(MouseEvent mouseEvent) {
        clearBoard();
    }

    @Override
    public void placeArrow(String color, String direction, int row, int column) {
        String imageName = "images/" + color + direction + ".png";
        Image img = new Image(imageName);

        for(Node node : board.getChildren()) {
            if(node instanceof ImageView) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    ((ImageView) node).setImage(img);
                    break;
                }
            }
        }
    }

    @Override
    public void removeArrow(int row, int column) {
        for(Node node : board.getChildren()) {
            if(node instanceof ImageView) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    ((ImageView) node).setImage(null);
                    break;
                }
            }
        }
    }

    @Override
    public void signalEndgame(String winner) {
        final Stage dialog = new Stage();
        dialog.setTitle("Game end.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("No more possible moves. \n " + winner + " wins!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> {
            dialog.close(); clearBoard();
        });
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void signalInvalidMove() {
        final Stage dialog = new Stage();
        dialog.setTitle("Error.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Invalid move!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> dialog.close());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void adjustInterButtons(boolean isVisible) {
        northEastButton.setVisible(isVisible);
        northWestButton.setVisible(isVisible);
        southEastButton.setVisible(isVisible);
        southWestButton.setVisible(isVisible);
    }

    private void clearBoard(){
        if(null == board) return;
        presenter.clearBoard();
        for(Node node : board.getChildren()) {
            if(node instanceof ImageView) {
                ((ImageView) node).setImage(null);
            }
        }
    }

    public void clickedUndoButton(MouseEvent mouseEvent) {
        presenter.undoMove();
    }

    private void setButtonImages(String color) {
        BackgroundImage nImage = new BackgroundImage(new Image("images/" + color + "N.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage neImage = new BackgroundImage(new Image("images/" + color + "NE.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage eImage = new BackgroundImage(new Image("images/" + color + "E.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage seImage = new BackgroundImage(new Image("images/" + color + "SE.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage sImage = new BackgroundImage(new Image("images/" + color + "S.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage swImage = new BackgroundImage(new Image("images/" + color + "SW.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage wImage = new BackgroundImage(new Image("images/" + color + "W.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage nwImage = new BackgroundImage(new Image("images/" + color + "NW.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        northButton.setBackground(new Background(nImage));
        northEastButton.setBackground(new Background(neImage));
        eastButton.setBackground(new Background(eImage));
        southEastButton.setBackground(new Background(seImage));
        southButton.setBackground(new Background(sImage));
        southWestButton.setBackground(new Background(swImage));
        westButton.setBackground(new Background(wImage));
        northWestButton.setBackground(new Background(nwImage));
    }
}
