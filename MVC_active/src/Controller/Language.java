package Controller;

import View.LanguageObserver;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Language extends Observable {
    private static ObjectProperty<Locale> locale;
    private static final ArrayList<Locale> supportedLocales = new ArrayList<>();
    private static Controller controller;

    public static final Locale ROMANIAN = new Locale("ro");
    public static final Locale ENGLISH = new Locale("en");
    public static final Locale FRENCH = new Locale("fr");
    public static final Locale SPANISH = new Locale("esp");

    static {
        locale = new SimpleObjectProperty<>(ROMANIAN);
        locale.addListener(((observableValue, oldValue, newValue) -> Locale.setDefault(newValue)));
    }

    public static List<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public static void setLocale(Locale locale, Controller controller) {
        localeProperty().set(locale);
        Locale.setDefault(locale);

        Stage stage = controller.getStage();
        stage.hide();
        FXMLLoader loader = new FXMLLoader(controller.getClass().getClassLoader().getResource("View/sample.fxml"));
        loader.setResources(ResourceBundle.getBundle("View.lang", locale));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Controller)loader.getController()).setStage(stage);
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    public static void setController(Controller cnt) {
        controller = cnt;
    }

}
