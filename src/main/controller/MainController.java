package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class MainController {

    private static final int INFORMATION_MESSAGE = 1;
    @FXML
    ComboBox comboBox;

    private String[] results;
    private Object FileWriteUtil;

    private Stage thisStage;//当前controller的Stage
    private Stage showStageTemp;


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
            inputStage.setScene(new Scene(inputRoot, 600, 550));
            inputStage.show();
        }

    }

    public void twice() throws Exception{
        URL location = getClass().getResource("../fxml/show.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent showRoot = fxmlLoader.load();


        //新建一个Stage
        Stage showStage = new Stage();
        //获取Controller的实例对象
        ShowController showController = fxmlLoader.getController();

        //showController.draw();
        showController.zoom();
        showController.drag();

        //set Icon
        showStage.getIcons().add(new Image("res/Image/icon.png"));
        showStage.setTitle("拓扑排序应用系统");
        showStage.setScene(new Scene(showRoot, 1200, 900));
        showStage.show();
        showStageTemp = showStage;


    }

    public void onShowBtnClicked() throws Exception {
        twice();
        //showStageTemp.close();
        //twice();
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
        //searchController.setStage(thisStage);

        searchController.draw();
        searchController.zoom();

        //set Icon
        searchStage.getIcons().add(new Image("res/Image/icon.png"));
        searchStage.setTitle("拓扑排序应用系统");
        searchStage.setScene(new Scene(searchRoot, 1200, 900));
        searchStage.show();
    }

    public void onExportBtnClicked() throws IOException {
        results = topologicalSorting();//接下来把结果写到文件

        boolean flag=true;//判断文件是否已存在，即文件名是否重复
        String[] results = {"拓","扑","排","序","应","用","系","统"};//测试用，删

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(thisStage);

        FileWriter writer = null;
        try {
            if(file.exists()){//文件已存在，则删除覆盖文件
                flag = false;
            }
            // 向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(file, flag);
            for(int i=0;i<results.length;i++)
            {
                writer.append(results[i]+"\r\n");//写入String[] results
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }

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