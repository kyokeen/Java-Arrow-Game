package Controller;

import Model.*;
import Model.Strategies.RandomStrategy;
import View.BoardObserver;
import javafx.collections.FXCollections;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

public class Controller {
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

    public Button buttonLangRO;
    public Button buttonLangEN;
    public Button buttonLangFR;
    public Button buttonLangESP;

    protected ImageView selectedImage;
    public String selectedDirection = "N";

    private String chosenColor = "g";
    private int chosenBoardSize = 4;
    private Game model;
    private Stage stage;

    @FXML
    private void initialize() {
        model = new Game(new SystemPlayer("r"), new Player("g"), new GameBoard(8));
        //model.getSystemPlayer().setStrategy(new MinMaxStrategy(2, 10));
        model.getSystemPlayer().setStrategy(new RandomStrategy());
        model.setBoardObserver(new BoardObserver(this));

        setButtonImages("g");
        loadChoiceBoxes();

        buttonLangRO.setOnAction(actionEvent -> Language.setLocale(Language.ROMANIAN, this));

        buttonLangEN.setOnAction(actionEvent -> Language.setLocale(Language.ENGLISH, this));

        buttonLangFR.setOnAction(actionEvent -> Language.setLocale(Language.FRENCH, this));

        buttonLangESP.setOnAction(actionEvent -> Language.setLocale(Language.SPANISH, this));

        colorSelectChoiceBox.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            chosenColor = newValue.toLowerCase().charAt(0) + "";
            setButtonImages(chosenColor);
            model.getSystemPlayer().setColor(model.getUserPlayer().getColor());
            model.getUserPlayer().setColor(chosenColor);
            model.changePlayerColor(model.getUserPlayer(), chosenColor);
            startGame();
        }));
        levelSelectChoiceBox.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.equals("4x4")) {
                chosenBoardSize = 4;
            } else {
                chosenBoardSize = 8;
            }
            startGame();
        }));
    }

    public void clickedBoard(MouseEvent mouseEvent) {
        EventTarget source = mouseEvent.getTarget();
        if (!(source instanceof ImageView)) return;
        selectedImage = (ImageView) source;
        int row = GridPane.getRowIndex(selectedImage);
        int column = GridPane.getColumnIndex(selectedImage);

        Arrow arrow = new Arrow(model.getUserPlayer().getColor(), selectedDirection);
        Move move = new Move(row, column, arrow);

        boolean valid = model.makeUserMove(move);
        if (!valid) {
            signalInvalidMove();
            return;
        }
        if (model.isEndgame()) {
            signalEndgame("User");
            return;
        }
        model.getSystemMove();
        if (model.isEndgame()) signalEndgame("Computer");
    }

    public void clickedArrowButton(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        selectedDirection = button.getText();
    }

    public void clickedStartGame(MouseEvent mouseEvent) {
        startGame();
    }

    private void loadChoiceBoxes() {
        colorSelectChoiceBox.setValue("Green");
        colorSelectChoiceBox.setItems(FXCollections.observableArrayList("Red", "Green"));
        levelSelectChoiceBox.setValue("4x4");
        levelSelectChoiceBox.setItems(FXCollections.observableArrayList("4x4", "8x8"));
    }

    public void clickedRestartButton(MouseEvent mouseEvent) {
        clearBoard();
    }

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
            dialog.close();
        });
        dialog.setScene(dialogScene);
        dialog.show();
        startGame();
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

    private void clearBoard() {
        if (null == board) return;
        model.clearBoard();
    }

    private void startGame() {
        clearBoard();
        if (null != board) board.setVisible(false);
        if (chosenBoardSize == 4) {
            board = smallBoard;
            changeLevel(4);
            adjustInterButtons(false);
        } else {
            board = largeBoard;
            changeLevel(8);
            adjustInterButtons(true);
        }
        board.setVisible(true);
    }

    public void clickedUndoButton(MouseEvent mouseEvent) {
        model.undo();
        model.undo();
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

    public void changeLevel(int boardSize) {
        model.changeBoardSize(boardSize);
    }

    public void clickedHintButton(MouseEvent mouseEvent) {
        if(null != board) {
            Move move = model.getHint();
            model.makeUserMove(move);
            if (model.isEndgame()) {
                signalEndgame("User");
                return;
            }
            model.getSystemMove();
            if (model.isEndgame()) signalEndgame("Computer");
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
