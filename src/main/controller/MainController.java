package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class MainController {

    @FXML
    ComboBox comboBox;

    private String[] results;

    private Stage thisStage;//当前controller的Stage

    public void onConfirmBtnClicked() throws Exception {
        String way = comboBox.getValue().toString();
        if (way.equals("导入")) {
            //动态加载窗口fxml界面
            URL location = getClass().getResource("../fxml/import.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent importRoot = fxmlLoader.load();


            //新建一个Stage
            Stage importStage = new Stage();
            //获取Controller的实例对象
            ImportController importController = fxmlLoader.getController();
            importController.setStage(thisStage);


            //set Icon
            importStage.getIcons().add(new Image("res/Image/icon.png"));
            importStage.setTitle("拓扑排序应用系统");
            importStage.setScene(new Scene(importRoot, 600, 400));
            importStage.show();
        } else if (way.equals("输入")) {
            //动态加载窗口fxml界面
            URL location = getClass().getResource("../fxml/input.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent inputRoot = fxmlLoader.load();


            //新建一个Stage
            Stage inputStage = new Stage();
            //获取Controller的实例对象
            InputController inputController = fxmlLoader.getController();


            //set Icon
            inputStage.getIcons().add(new Image("res/Image/icon.png"));
            inputStage.setTitle("拓扑排序应用系统");
            inputStage.setScene(new Scene(inputRoot, 600, 400));
            inputStage.show();
        }

    }

    public void onShowBtnClicked() throws Exception {
        //动态加载窗口fxml界面
        URL location = getClass().getResource("../fxml/show.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent showRoot = fxmlLoader.load();


        //新建一个Stage
        Stage showStage = new Stage();
        //获取Controller的实例对象
        ShowController showController = fxmlLoader.getController();


        //set Icon
        showStage.getIcons().add(new Image("res/Image/icon.png"));
        showStage.setTitle("拓扑排序应用系统");
        showStage.setScene(new Scene(showRoot, 600, 400));
        showStage.show();


    }

    public void onSearchBtnClicked() throws Exception {
        //动态加载窗口fxml界面
        URL location = getClass().getResource("../fxml/search.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent searchRoot = fxmlLoader.load();


        //新建一个Stage
        Stage searchStage = new Stage();
        //获取Controller的实例对象
        SearchController searchController = fxmlLoader.getController();
        searchController.setStage(thisStage);


        //set Icon
        searchStage.getIcons().add(new Image("res/Image/icon.png"));
        searchStage.setTitle("拓扑排序应用系统");
        searchStage.setScene(new Scene(searchRoot, 600, 400));
        searchStage.show();
    }

    public void onExportBtnClicked() {
        results = topologicalSorting();//接下来把结果写到文件

    }

    public void onExitBtnClicked() {
        System.exit(0);
    }

    public String[] topologicalSorting() {
        String[] results = new String[100];
        return results;
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}
