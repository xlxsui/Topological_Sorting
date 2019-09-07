package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportController {

    private Stage thisStage;//当前controller的Stage
    private theResults[] resultsC = new theResults[100];//结构体，此时string是课程名
    private theResults[] resultsI = new theResults[100];//结构体，此时string是课程序号
    private String[] courses = new String[100];//课程名数组，给每个课程序号
    private String[] results = new String[100];//最终传出的数据
    @FXML
    private TextField textfield;
    private String path;
    int relationCount,elemCount;

    public void onSelectBtnClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(thisStage);

        String path = file.getPath();//选择的文件地址
        textfield.setText(path);
        System.out.println("你通过文件选择器打开了："+file);//运行框显示打开的文件地址以便检查

    }

    public static String aComaB(String msg) {//去掉尖括号
        String list=new String();
        Pattern p = Pattern.compile("<(.*?)>");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list=(m.group(1));
        }
        return list;
    }

    public String codeWay(String path) throws IOException {//获取当前txt文件的编码格式
        BufferedInputStream bin =new BufferedInputStream(new FileInputStream(path));
        int p =(bin.read()<<8)+bin.read();

        String code =null;

        switch(p){
            case 0xefbb:
                code ="UTF-8";
                break;
            case 0xfffe:
                code ="Unicode";
                break;
            case 0xfeff:
                code ="UTF-16BE";
                break;
            default:
                code ="GBK";
        }
        return code;
    }

    public void onConfirmBtnClicked() throws IOException {
        path = textfield.getText();//获取textfield里的路径
        System.out.println("按下“确认”后取到的路径（即此时TextField的文本）为："+path);//测试用
        File filename = new File(path); // 要读取path路径的input,txt文件
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename),codeWay(path)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line;
        int i=0,j=0,k;
        while ((line = br.readLine()) != null) {
            // 一次读入一行数据line = br.readLine()
            //以下是和String[] resultsC 和String[] resultsI 有关的代码
            String[] string = aComaB(line).split(",");//"<a,b>"字符串变“a”和“b”
            resultsC[i] = new theResults();//实例化theResults对象

            resultsC[i].front = string[0];//前面的字符串存到front
            System.out.println(resultsC[i].front);
            resultsC[i].rear = string[1];//后面的字符串存到rear
            System.out.println(resultsC[i].rear);
            i++;
            System.out.println(" ");//
        }

        //String s = "程序设计基础";
        //String s1 = "程序设计基础";
        //System.out.println( s1.equals(s));

        courses[0]= resultsC[0].front;
        System.out.println("0:"+courses[0] );//
        for(k=0;k<i;k++){//此时j是courses的序号，从0开始
            System.out.println("第"+k+"行" );
            j=0;
            int flag1=0,flag2=0;
            while (courses[j]!=null) {
                if(courses[j].equals(resultsC[k].front))//如果有相同的名字  标志值变1
                    flag1=1;
                else if(courses[j].equals(resultsC[k].rear))
                        flag2=1;
                j++;//最后的j是当前course数组所存有的课程总数
            }
            if(flag1==0&&flag2!=0)
                courses[j]=resultsC[k].front;
            else if(flag2==0&&flag1!=0)
                courses[j] = resultsC[k].rear;
            else if(flag1==0&&flag2==0){
                courses[j]=resultsC[k].front;
                courses[++j]=resultsC[k].rear;
            }
            else continue;

            System.out.println(j+":"+courses[j] );
            if(courses[j+1]!=null)
                System.out.println((j+1)+":"+courses[j+1] );
        }

        System.out.println("课程数："+j);
        elemCount = j;
        System.out.println("关系数："+i);
        relationCount = i;

        int lineNum = i, courseNum = j;
        i--;
        while (i>=0){
            resultsI[i] = new theResults();//实例化theResults对象
            for(j=0;j<courseNum;j++) {
                if((resultsC[i].front).equals(courses[j])) {
                    resultsI[i].front = j + "";//下标数字转换成字符
                }
                if((resultsC[i].rear).equals(courses[j])) {
                    resultsI[i].rear = j + "";//下标数字转换成字符
                }
            }
            i--;
        }//彻底把resultsC[]转换成resultsI[]
        for(i=0; i<lineNum; i++){//把“0” “1”变成“0，1”
            String s= i+"";
            results[i]= s+","+resultsI[i].front + "，" + resultsI[i].rear;
            System.out.println("results["+i+"]:"+results[i]);
        }
        syncData();
    }

    public void syncData() {
        MainController.N = relationCount;
        MainController.elemCount = elemCount;

        for (int i = 0; i < elemCount; i++) {
            MainController.elements[i] = courses[i];
        }

        for (int i = 0; i < relationCount; i++) {
            MainController.relationships[i].front = resultsC[i].front;
            MainController.relationships[i].rear = resultsC[i].rear;
        }
    }

    public void onBackBtnClicked() {
        thisStage.close();
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        thisStage = stage;
    }
}

class theResults {
    String front = "";
    String rear = "";
}



