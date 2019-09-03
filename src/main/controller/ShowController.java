package main.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import GraphViz.GraphViz;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.control.ScrollPane;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import main.Main;

public class ShowController {

    @FXML
    ScrollPane scrollPane;
    @FXML
    WebView webView;
    ImageView imageView = new ImageView();
    theResult[] relationships = MainController.relationships;
    private Stage thisStage;//当前controller的Stage

    GraphViz gViz = new GraphViz("showGif","D:\\", "D:\\Graphviz\\bin\\dot.exe");

    public void showResults(String[] results) {

    }

    public void topologicalSorting() {

    }



    public void showHTML() throws URISyntaxException, IOException {
        String url = Main.class.getResource("/main/controller/index.html").toExternalForm();
        Desktop.getDesktop().browse(URI.create(url));
    }

    public void draw() throws MalformedURLException {
        int N=MainController.N;
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

        //等待relationships变量，即可启用
        gViz.start_graph();
        gViz.addln("node [fontname=\"SimHei\",size=\"15,15\",sides=5,color=lightblue,style=filled];");
        for(int i=0;i<N;i++){
            gViz.addln(relationships[i].front + "->" + relationships[i].rear + ";");
        }
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        gViz.start_graph();
        gViz.addln("node [fontname=\"SimHei\",size=\"15,15\",sides=5,color=lightblue,style=filled];");
        gViz.addln("\"数据结构\"->\"程序设计基础\";");
        gViz.addln("A->U;");
        gViz.addln("C->B;");
        gViz.addln("B->D;");
        gViz.addln("C->E;");
        gViz.addln("C->L;");
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public void zoom() throws MalformedURLException {

        draw();
        File file = new File("D:/showGif.gif");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);

        imageView.setImage(image);
        imageView.preserveRatioProperty().set(true);
        scrollPane.setContent(imageView);
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}