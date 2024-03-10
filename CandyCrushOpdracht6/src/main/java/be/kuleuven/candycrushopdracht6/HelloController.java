package be.kuleuven.candycrushopdracht6;
import java.net.URL;
import java.util.ResourceBundle;

import be.kuleuven.candycrushopdracht6.model.CandycrushModel;
import be.kuleuven.candycrushopdracht6.view.CandycrushView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HelloController {


    public javafx.scene.control.Label playerAndScore;
    public TextField nameInput;
    public javafx.scene.control.Label loginText;
    public javafx.scene.control.Label score;
    public Button reset;
    public Button startButton;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Label;


    @FXML
    private AnchorPane paneel;

    @FXML
    private AnchorPane speelbord;

    @FXML
    private TextField textInput;

    private CandycrushModel model;
    private CandycrushView view;
    @FXML
    void initialize() {
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert startButton != null : "fx:id=\"btn\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert paneel != null : "fx:id=\"paneel\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert speelbord != null : "fx:id=\"speelbord\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        model = new CandycrushModel("Test");
        view = new CandycrushView(model);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onCandyClicked);
    }

    public void update(){
        view.update();
    }

    public void onCandyClicked(MouseEvent me){
        int candyIndex = view.getIndexOfClicked(me);
        model.candyWithIndexSelected(candyIndex);

        playerAndScore.setText(nameInput.getText() + ": " + Integer.toString(model.getScore()));
        update();
    }


    public void LoginButton(ActionEvent actionEvent) {


        playerAndScore.setText(nameInput.getText() + ": " + Integer.toString(model.getScore()));
        reset.setVisible(true);
        playerAndScore.setVisible(true);
        speelbord.setVisible(true);

        nameInput.setVisible(false);
        startButton.setVisible(false);
        loginText.setVisible(false);
        update();
    }

    public void ResetButton(ActionEvent actionEvent) {


        //playerAndScore.setText(nameInput.getText());
        reset.setVisible(false);
        playerAndScore.setVisible(false);
        speelbord.setVisible(false);

        model.setScore(0);

        nameInput.setVisible(true);
        startButton.setVisible(true);
        loginText.setVisible(true);
        update();
    }
}