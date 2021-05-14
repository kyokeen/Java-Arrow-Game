package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        Locale locale = new Locale("ro");
        ResourceBundle bundle = ResourceBundle.getBundle("View.lang", locale);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View/sample.fxml"), bundle);
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Arrow Game");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws InterruptedException {
        //pplication.launch(Controller.class, args);
        launch(args);
    }
}
