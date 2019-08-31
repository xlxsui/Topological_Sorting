package main.controller;

import javafx.stage.Stage;

public class ImportController {

    private Stage thisStage;//当前controller的Stage

    public void onSelectBtnClicked() {

    }

    public void onConfirmBtnClicked() {

    }

    public void onBackBtnClicked() {

    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}


