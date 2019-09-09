package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.WelcomeController;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //动态加载窗口fxml界面
        URL location = getClass().getResource("/main/fxml/welcome.fxml");//getResource是定位到当前类目录，..jar返回不了，注意大小写。/开头定位到根目录，相当于src
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();


        //获取welcome界面的Controller的实例对象
        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setStage(primaryStage);//让控制器获取到Stage


        //set Icon
        primaryStage.getIcons().add(new Image(getClass().getResource("/main/resources/image/icon.png").toExternalForm()));
        primaryStage.setTitle("拓扑排序应用系统");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
