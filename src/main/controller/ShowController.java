package main.controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.MalformedURLException;



public class ShowController {

    private Stage thisStage;//当前controller的Stage
    @FXML
    ImageView imageView;
    public void showResults(String[] results) {

    }

    public void topologicalSorting() {

    }

    public void draw() throws MalformedURLException {
        File file = new File("D:/dotGif.png");
        String localUrl = file.toURI().toURL().toString();
        Image image  = new Image(localUrl);
        imageView.setImage(image);
    }

    public void zoom() {

    }

    public void drag() {

    }

    public void init() throws MalformedURLException {
        ShowController heihei = new ShowController();
        heihei.draw();
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;

    }
}
