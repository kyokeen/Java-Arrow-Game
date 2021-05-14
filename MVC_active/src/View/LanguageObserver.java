package View;

import Controller.Controller;
import java.util.Observable;
import java.util.Observer;

public class LanguageObserver implements Observer {
    Controller controller;

    @Override
    public void update(Observable o, Object arg) {
        //read from fxml
        String lang = (String)arg;
        System.out.println("Changed language to " + lang);
    }
}
