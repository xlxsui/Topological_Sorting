package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class WelcomeController {

    private Stage thisStage;//当前controller的Stage

    public void onWelcomeBtnClicked() throws Exception {
        //动态加载窗口fxml界面
        URL location = getClass().getResource("../fxml/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent mainRoot = fxmlLoader.load();


        //新建一个Stage
        Stage mainStage = new Stage();
        //获取Controller的实例对象
        MainController mainController = fxmlLoader.getController();
        mainController.setStage(mainStage);


        //set Icon
        mainStage.getIcons().add(new Image("res/Image/icon.png"));
        mainStage.setTitle("拓扑排序应用系统");
        mainStage.setScene(new Scene(mainRoot, 600, 400));
        mainStage.show();

        //当前窗口关闭
        thisStage.close();
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}
