package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class ImportController {

    private Stage thisStage;//当前controller的Stage
    private String path;
    private String[] results = new String[100];//测试用,删
    @FXML
    private TextField textfield;

    public void onSelectBtnClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(thisStage);

        path = file.getPath();//选择的文件地址
        textfield.setText(path);
        System.out.println("你通过文件选择器打开了："+file);//运行框显示打开的文件地址以便检查

    }

    public void onConfirmBtnClicked() throws IOException {
        String path = textfield.getText();//获取textfield里的路径
        System.out.println("按下“确认”后取到的路径（即此时TextField的文本）为："+path);//测试用
        File filename = new File(path); // 要读取path路径的input,txt文件
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line;
        int i=0;
        while ((line = br.readLine()) != null)  {
            // 一次读入一行数据line = br.readLine()
            //以下是和String[] resuts 有关的代码
            i++;
            results[i]=line;
            System.out.println(results[i]);//运行框显示读出来的内容以便检查
        }
    }

    public void onBackBtnClicked() {
        thisStage.hide();
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}




