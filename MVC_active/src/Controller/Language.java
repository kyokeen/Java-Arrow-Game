package Controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Language {
    private static final ObjectProperty<Locale> locale;
    private static final ArrayList<Locale> supportedLocales = new ArrayList<>();

    public static final Locale ROMANIAN = new Locale("ro");
    public static final Locale ENGLISH = new Locale("en");
    public static final Locale FRENCH = new Locale("fr");
    public static final Locale SPANISH = new Locale("esp");

    static {
        supportedLocales.add(ROMANIAN);
        supportedLocales.add(ENGLISH);
        supportedLocales.add(FRENCH);
        supportedLocales.add(SPANISH);

        locale = new SimpleObjectProperty<>(ROMANIAN);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public static void setLocale(Locale locale, Controller controller) {
        if (!supportedLocales.contains(locale)) {
            return;
        }
        localeProperty().set(locale);
        Locale.setDefault(locale);

        Stage stage = controller.getStage();
        stage.hide();
        FXMLLoader loader = new FXMLLoader(controller.getClass().getClassLoader().getResource("View/sample.fxml"));
        loader.setResources(ResourceBundle.getBundle("View.lang", locale));

        Parent root;
        try {
            root = loader.load();
            ((Controller)loader.getController()).setStage(stage);
            stage.setScene(new Scene(root, 700, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
