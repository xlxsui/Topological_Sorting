package main.controller;

import GraphViz.GraphViz;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import main.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class SearchController {
    @FXML
    ScrollPane scrollPane;
    ImageView imageView = new ImageView();
    GraphViz gViz=new GraphViz("D:\\","D:\\Graphviz\\bin\\dot.exe");

    private Stage thisStage;//当前controller的Stage

    public void onSearchBtnClicked() {

    }

    public void showResults(String[] results) {

    }

    public void topologicalSorting() {

    }

    public void showHTML() throws URISyntaxException, IOException {
        String url = Main.class.getResource("/HTML/index.html").toExternalForm();
        Desktop.getDesktop().browse(URI.create(url));
    }

    public void draw() throws MalformedURLException {
        DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 2);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });
        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.2);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });

        gViz.start_graph();
        gViz.addln("node [fontname=\"SimHei\",size=\"15,15\",shape=polygon,sides=5,peripheries=3,color=lightblue,style=filled];");
        gViz.addln("\"数据结构\"->\"程序设计基础\";");
        gViz.addln("A->C;");
        gViz.addln("C->B;");
        gViz.addln("B->D;");
        gViz.addln("C->E;");
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zoom() throws MalformedURLException {
        File file = new File("D:/dotGif.gif");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);

        imageView.setImage(image);
        imageView.preserveRatioProperty().set(true);
        scrollPane.setContent(imageView);
    }

    public void drag() {

    }


    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}
