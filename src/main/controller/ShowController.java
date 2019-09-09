package main.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.control.TextArea;
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
import java.util.*;
import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import main.Main;

public class ShowController {
    static String Str;
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextArea showText;

    ImageView imageView = new ImageView();

    theResult[] relationships = MainController.relationships;

    private Stage thisStage;//当前controller的Stage

    int maxn = 3000;   //最多给输出1万种可能

    int N = MainController.N;

    String[] elements = MainController.elements;

    public class tr {
        String front;
        String rear;
    }

    private class Edge {  //边
        private Vertex endVertex;

        public Edge(Vertex endVertex) {
            this.endVertex = endVertex;
        }
    }

    private class Vertex {   //点
        private int vertexLabel = 0;   //该点的序号
        private List<Edge> adjEdges;   //以该点为起点的边
        private int inDegree = 0;  //该点的入度

        public Vertex(int verTexLabel) {
            this.vertexLabel = verTexLabel;
            this.inDegree = 0;
            this.adjEdges = new LinkedList<>();  //new了一个链表
        }
    }

    ArrayList<Vertex> vertexs = new ArrayList();

    ArrayList<Integer> verIndex = new ArrayList();

    int cnt = 0;

    String graphContent = MainController.graphContent;

    int n = -1; //记录顶点的个数

    String[] topoResults = new String[maxn];  //用来记录结果

    int numTopoResult = 0;  //用来记录结果数

    int[] ans = new int[maxn];  //答案字符串

    int[] visit = new int[100];

    tr[] normal = new tr[N]; //normal用来存储正常的关系对

    String[] justOne = new String[N];  //JustOne用来存储没有先修关系的课程

    int[] justOneIndex = new int[N];  //用来记录没有先修关系课程的序号

    GraphViz gViz = new GraphViz("showGif", "D:\\", "D:\\Graphviz\\bin\\dot.exe");

    public void showResults() {
        topologicalSorting();
        String ok = "";
        if (cnt < n) {
            ok = "输入关系成环，无法进行拓扑排序！";
        } else {
            int[] topo = new int[n];

            for (int i = 0; i < numTopoResult; i++) {
                String showText = "第" + (i + 1) + "种：\n";
                String[] elem = topoResults[i].split(",");
                for (int j = 0; j < n; j++) topo[j] = Integer.parseInt(elem[j]);
                for (int j = 0; j < n - 1; j++) showText += elements[topo[j]] + "->";
                showText += elements[topo[n - 1]] + "\n\n";
                ok += showText;
            }
        }
        Str = ok;
        showText.setText(ok);

    }

    public void buildGraph(String graphContent) {  //传入的是名为graphContent的字符
        Vertex o = new Vertex(0);
        vertexs.add(o);
        verIndex.add(0);
        n++;
        String[] lines = graphContent.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] nodesInfo = lines[i].split(",");
            int startNodeLabel = Integer.parseInt(nodesInfo[1]);
            int endNodeLabel = Integer.parseInt(nodesInfo[2]);
            Vertex startNode = new Vertex(startNodeLabel);
            Vertex endNode = new Vertex(endNodeLabel);

            if (startNodeLabel != 0) {
                if (verIndex.contains(endNodeLabel)) {
                    vertexs.get(endNodeLabel).inDegree++;
                } else {
                    endNode.inDegree++;  //对有先修关系的点的入度+1
                }
            }

            if (!verIndex.contains(startNodeLabel)) {
                verIndex.add(startNodeLabel);
                vertexs.add(startNode);
                n++;
            }
            if (!verIndex.contains(endNodeLabel)) {   //al是所有的顶点的集合
                vertexs.add(endNode);
                verIndex.add(endNodeLabel);
                n++;
            }

            if (startNodeLabel != 0) {
                Vertex v = vertexs.get(endNodeLabel);
                Edge e = new Edge(v);
                Vertex v1 = vertexs.get(startNodeLabel);
                v1.adjEdges.add(e);
            }
        }
    }

    public void dfs(int t) {
        ArrayList<Vertex> vertexsback = new ArrayList();
        if (numTopoResult >= 3000) {
            return;
        }
        if (t == n) {                                           //如果结果成立则将结果赋值
            for (int i = 0; i < n; i++) {                             //将排序结果循环加入
                topoResults[numTopoResult] += ans[i] + ",";
            }
            numTopoResult++;
        }

        for (int i = 1; i <= n; i++) {
            Vertex v = vertexs.get(i);//循环取出点
            if ((v.inDegree == 0) && (visit[v.vertexLabel] == 0)) {//如果点的入度为零并且点没有被访问过

                for (int k = 0; k < v.adjEdges.size(); k++) {
                    v.adjEdges.get(k).endVertex.inDegree--;
                    vertexsback.add(v.adjEdges.get(k).endVertex);
                }

                v.adjEdges.clear();//清空所有邻接点
                visit[v.vertexLabel] = 1;
                ans[t] = v.vertexLabel;
                cnt += 1;
                dfs(t + 1);

                for (int k = 0; k < vertexsback.size(); k++) {
                    Vertex v1 = vertexsback.get(k);
                    v1.inDegree++;
                    Edge e = new Edge(v1);
                    v.adjEdges.add(e);
                }
                visit[i] = 0;
            }
        }
        return;
    }

    public void topologicalSorting() {
        for (int i = 0; i <= n; i++) {
            visit[i] = 0;
        }
        for (int i = 0; i < maxn; i++) {
            topoResults[i] = new String();
        }
        for (int i = 0; i < maxn; i++) {
            ans[i] = 0;
        }
        buildGraph(graphContent);
        dfs(0);
    }

    public void showHTML() throws URISyntaxException, IOException {
        String url = Main.class.getResource("/main/controller/index.html").toExternalForm();
        Desktop.getDesktop().browse(URI.create(url));
    }

    public void draw() throws MalformedURLException {

        for (int i = 0; i < normal.length; i++)
            normal[i] = new tr();

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

        //将单个的课程存储下来，并且记录他们的序号
        int numJustOne = 0;
        for (int i = 0; i < N; i++) {
            if (relationships[i].front.equals("无")) {
                justOne[numJustOne] = relationships[i].rear;
                justOneIndex[numJustOne] = i;
                numJustOne++;
            }
        }

        //将不是单个的放进normal数组
        int normalIndex = 0;
        int isIn = 0;
        for (int i = 0; i < N; i++) {
            isIn = 0;
            for (int j = 0; j < numJustOne; j++) {
                if (i == justOneIndex[j]) {
                    isIn = 1;
                    break;
                }
            }
            if (isIn == 0) {
                normal[normalIndex].front = relationships[i].front;
                normal[normalIndex].rear = relationships[i].rear;
                normalIndex++;
            }
        }

        //等待relationships变量，即可启用
        gViz.start_graph();
        gViz.addln("node [fontname=\"SimHei\",size=\"15,15\",sides=5,color=lightblue,style=filled];");
        for (int i = 0; i < numJustOne; i++) {
            gViz.addln(justOne[i] + ";");
        }
        for (int i = 0; i < normalIndex; i++) {
            gViz.addln(normal[i].front + "->" + normal[i].rear + ";");
        }
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
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