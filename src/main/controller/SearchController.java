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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class SearchController {
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextField searchField;
    @FXML
    TextArea resultArea;

    int N=MainController.N;

    //记录搜索的字符串
    String searching;

    //与搜索字符串相关的关系对的数量
    int numtr = 0; //记录和输入搜索课程相关的关系对的数量

    tr[] trip=new tr[N];  //trip用来储存有关用户搜索的关键字的关系对

    tr[] normal = new tr[N]; //normal用来存储正常的关系对

    String[] justOne = new String[N];  //JustOne用来存储没有先修关系的课程

    int[] justOneIndex = new int[N];  //用来记录没有先修关系课程的序号

    theResult[] relationships = MainController.relationships;

    ImageView imageView = new ImageView();

    GraphViz gViz=new GraphViz("searchGif","D:\\","D:\\Graphviz\\bin\\dot.exe");

    public class tr{   //tr记录和输入搜索课程相关的关系对
        String front;
        String rear;
    }

    private Stage thisStage;//当前controller的Stage

    public void onSearchBtnClicked() {
        searching = searchField.getText();
    }

    public void showResults() {
        String area = "";
        for(int i=0;i<numtr;i++){
            area += "<" + trip[i].front + "," + trip[i].rear + ">\n";
        }
        resultArea.appendText(area);
    }

    public void showHTML() throws URISyntaxException, IOException {
        String url = Main.class.getResource("/main/controller/index.html").toExternalForm();
        Desktop.getDesktop().browse(URI.create(url));
    }

    public void draw() throws MalformedURLException {
        tr[] untrip=new tr[N];  //trip用来储存无关用户搜索的关键字的关系对

        for (int i=0; i<normal.length; i++)
            normal[i] = new tr();

        //将单个的课程存储下来，并且记录他们的序号
        int numJustOne = 0;
        for(int i=0;i<N;i++){
            if(relationships[i].front.equals("无")){
                justOne[numJustOne] = relationships[i].rear;
                justOneIndex[numJustOne] = i;
                numJustOne ++;
            }
        }

        //将不是单个的放进normal数组
        int normalIndex = 0;
        int isIn=0;
        for(int i=0;i<N;i++){
            isIn=0;
            for(int j=0;j<numJustOne;j++){
                if(i == justOneIndex[j]){
                    isIn = 1;
                    break;
                }
            }
            if(isIn == 0){
                normal[normalIndex].front = relationships[i].front;
                normal[normalIndex].rear = relationships[i].rear;
                normalIndex ++;
            }
        }

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

        //new两个数组，trip记录和搜索有关的关系对，untrip记录无关的关系对
        for (int i=0; i<trip.length; i++)
            trip[i] = new tr();
        for (int i=0; i<untrip.length; i++)
            untrip[i] = new tr();

        String[] justOneuntrip = new String[N];   //用来记录与搜索无关的点

        //J搜索和用户输入的课程相关的关系放进trip
        for(int i = 0; i<normalIndex; i++){
            if(normal[i].front.equals(searching)||normal[i].rear.equals(searching)){
                trip[numtr].front = normal[i].front;
                trip[numtr].rear = normal[i].rear;
                numtr += 1;
            }
        }

        int theOne = -1;  //单个序号
        //搜索和用户输入相关的单个课程
        for(int i = 0; i<numJustOne; i++){
            if(justOne[i].equals(searching)){
                theOne = i;
                break;
            }
        }

        int other = 0;
        for(int i=0;i<numJustOne;i++){
            if(i != theOne){
                justOneuntrip[other] = justOne[i];
                other ++;
            }
        }

        int[] record = new int[N];
        int numRecord = 0;
        int i = 0;
        while(i < normalIndex){  //对于每个有关的关系对
            for(int j=0;j<normalIndex;j++){
                if((normal[j].front.equals(trip[i].front))&&(normal[j].rear.equals(trip[i].rear))){
                    //如果找到属于trip的关系对，记录它的序号
                    record[numRecord] = j;
                    numRecord ++;
                    break;
                }
            }
            i++;
        }

        int[] unrecord = new int[N]; //unrecord记录不相关关系对的序号
        int numUnRecord = 0;
        int flag;
        for(int y=0;y<normalIndex;y++){
            flag = 0;
            for(int yy=0;yy<numRecord;yy++){
                if(y == record[yy]){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                unrecord[numUnRecord] = y;
                numUnRecord ++;
            }
        }

        //对位于unrecord数组中的关系对，在untrip中进行添加
        for(int k=0;k<numUnRecord;k++){
            untrip[k].front = normal[unrecord[k]].front;
            untrip[k].rear = normal[unrecord[k]].rear;
        }

        //等待relationships变量，即可启用
        gViz.start_graph();
        gViz.addln("node [fontname=\"SimHei\",size=\"15,15\",sides=5,color=lightblue,style=filled];");
        gViz.addln(searching+"[color=red];");  //对该点标红
        for(int j=0;j<numUnRecord;j++){  //对线条标红
            gViz.addln(untrip[j].front + "->" + untrip[j].rear + "[color=red];");
        }
        for(int j=0;j<numJustOne;j++){  //画单个点
            gViz.addln(justOneuntrip[j] + ";");
        }
        for(int j=0;j < numRecord;j++){  //再画剩下的关系对
            gViz.addln(trip[j].front + "->" + trip[j].rear + ";");
        }
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /*
        gViz.start_graph();
        gViz.addln("node [fontname=\"SimHei\",size=\"15,15\",shape=polygon,sides=5,peripheries=3,color=lightblue,style=filled];");
        gViz.addln("\"数据结构\"->\"程序设计基础\";");
        gViz.addln("A->C;");
        gViz.addln("A->C;");
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
    */
    public void zoom() throws MalformedURLException {
        File file = new File("D:/searchGif.gif");
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
