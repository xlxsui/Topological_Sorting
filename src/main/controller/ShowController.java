package main.controller;

import javafx.stage.Stage;

public class ShowController {

    private Stage thisStage;//当前controller的Stage

    public void showResults(String[] results) {

    }

    public void topologicalSorting() {

    }

    public void draw() {

    }

    public void zoom() {

    }

    public void drag() {

    }


    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}
