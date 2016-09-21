package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * Created by Edy Junior on 21/09/2016.
 */
public class ScreensController extends AnchorPane {

    private HashMap <String, Node> screens = new HashMap<>();

    public  ScreensController() {
        super();
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String name, String resource) {

        try{

            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenController = (ControlledScreen) myLoader.getController();
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {

            System.out.print(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {

        if(screens.get(name) != null) {

            final DoubleProperty opacity = opacityProperty();

            if(!getChildren().isEmpty()) {

                Timeline fade = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 10)),
                    new KeyFrame(new Duration(1000), e -> {
                        getChildren().remove(0);
                        getChildren().add(0, screens.get(name));
                        Timeline fadeIn = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                            new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                    new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.print("A tela não pode ser carregada!");
            return false;
        }
    }

    public boolean unloadScreen(String name) {

        if (screens.remove(name) == null) {

            System.out.print("A tela não existe!");
            return false;
        } else {
            return true;
        }
    }
}
