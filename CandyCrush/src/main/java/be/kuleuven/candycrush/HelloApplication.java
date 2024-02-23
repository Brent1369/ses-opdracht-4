package be.kuleuven.candycrush;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;



import static be.kuleuven.candycrush.CheckNeighboursInGrid.getSameNeighboursIds;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();

        Iterable<Integer> iterabletest = List.of(   0, 0, 1, 0,
                                                    1, 1, 0, 2,
                                                    2, 0, 1, 3,
                                                    0, 1, 1, 1 );

        iterabletest = getSameNeighboursIds(iterabletest, 4, 4, 5);

        int test2 = 1;

    }
}