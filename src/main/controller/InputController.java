package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Elem {
    private int num;
    private String name;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Elem{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

}

class Relation {
    private int num, front, rear;

    @Override
    public String toString() {
        return "Relation{" +
                "num=" + num +
                ", front=" + front +
                ", rear=" + rear +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }


}

class Relationship {
    private String front, rear;

    @Override
    public String toString() {
        return "Relationship{" +
                "front='" + front + '\'' +
                ", rear='" + rear + '\'' +
                '}';
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getRear() {
        return rear;
    }

    public void setRear(String rear) {
        this.rear = rear;
    }
}

public class InputController {

    private Stage thisStage;//当前controller的Stage

    @FXML
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    TextArea textArea;


    Elem[] elements = new Elem[100];
    int elemCount = 0;
    Relation[] relations = new Relation[1000];
    Relationship[] relationships = new Relationship[1000];
    int relationCount = 0;
    int index = 0;

    /**
     * 初始化成员变量
     */
    public void init() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new Elem();
        }
        for (int i = 0; i < relations.length; i++) {
            relations[i] = new Relation();
        }
        for (int i = 0; i < relationships.length; i++) {
            relationships[i] = new Relationship();
        }

        elements[elemCount].setNum(elemCount);
        elements[elemCount].setName("无");
        elemCount++;
    }

    public void onClearBtnClicked() {
        textField1.setText("");
        textField2.setText("");
    }

    public void onAddBtnClicked() {

        //先判断输入内容是否有毒,输入有两种情况，输入一个（后面的输入框）或者两个
        if (isTextOk()) {
            //把输入内容存到elements先，要判断重复
            if (isOneInput()) {//没有先修的，就只有后面那个输入
                //是否重复
                if (!isElemRepeat(textField2.getText())) {
                    elements[elemCount].setNum(elemCount);
                    elements[elemCount].setName(textField2.getText());
                    elemCount++;
                }
            } else {
                if (!isElemRepeat(textField1.getText())) {
                    elements[elemCount].setNum(elemCount);
                    elements[elemCount].setName(textField1.getText());
                    elemCount++;
                }
                if (!isElemRepeat(textField2.getText())) {
                    elements[elemCount].setNum(elemCount);
                    elements[elemCount].setName(textField2.getText());
                    elemCount++;
                }
            }

            showContent();
            //接下来存到relations数组里面，也是两种情况
            if (isOneInput() && !isRelationRepeat()) {

                relations[relationCount].setNum(relationCount);
                relations[relationCount].setFront(getElemNum(elements, "无"));
                relations[relationCount].setRear(getElemNum(elements, textField2.getText()));

                relationships[relationCount].setFront("无");
                relationships[relationCount].setRear(textField2.getText());
                relationCount++;
            } else if (!isRelationRepeat()) {
                relations[relationCount].setNum(relationCount);
                relations[relationCount].setFront(getElemNum(elements, textField1.getText()));
                relations[relationCount].setRear(getElemNum(elements, textField2.getText()));

                relationships[relationCount].setFront(textField1.getText());
                relationships[relationCount].setRear(textField2.getText());
                relationCount++;
            }


        }
        index = relationCount;//都指向最后元素的下一个

        showContent();
        System.out.println("index：" + index + ", relationCount：" + relationCount);
        textArea.setText(makeShowText());
        syncData();//同步数据去MainController
    }

    public void onModifyBtnClicked() {
        if (index == relationCount) {
            index--;
        }

        if (index >= 0 && index < relationCount && !isRelationRepeat()) {
            relations[index].setFront(getElemNum(elements, textField1.getText()));
            relations[index].setRear(getElemNum(elements, textField2.getText()));

            relationships[index].setFront(textField1.getText());
            relationships[index].setRear(textField2.getText());
        }

        textArea.setText(makeShowText());
        showContent();
        System.out.println("index：" + index + ", relationCount：" + relationCount);
        syncData();//同步数据去MainController
    }

    public void onLastBtnClicked() {
        if (index > 0) {
            index--;
            textField1.setText(relationships[index].getFront());
            textField2.setText(relationships[index].getRear());
        }

        System.out.println("index：" + index + ", relationCount：" + relationCount);
    }

    public void onNextBtnClicked() {
        if (index < relationCount) {
            index++;
            textField1.setText(relationships[index].getFront());
            textField2.setText(relationships[index].getRear());
        }

        System.out.println("index：" + index + ", relationCount：" + relationCount);
    }

    public void onDeleteBtnClicked() {

        if (index == relationCount && index != 0) {
            index--;
        }
        if (index >= 0 && index < relationCount) {
            for (int i = index; i < relationCount; i++) {
                relations[i].setFront(relations[i + 1].getFront());
                relations[i].setRear(relations[i + 1].getRear());

                relationships[i].setFront(relationships[i + 1].getFront());
                relationships[i].setRear(relationships[i + 1].getRear());

            }
        }
        if (relationCount != 0) {
            relationCount--;
        }

        textArea.setText(makeShowText());
        showContent();
        System.out.println("index：" + index + ", relationCount：" + relationCount);
        syncData();//同步数据去MainController
    }

    public void onExportBtnClicked() throws Exception {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(thisStage);

        FileWriter writer = null;
        boolean flag = true;//判断文件是否已存在，即文件名是否重复
        try {
            if (file.exists()) {//文件已存在，则删除覆盖文件
                flag = false;
            }
            // 向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(file, flag);
            writer.append(makeShowText());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
    }


    /**
     * 显示错误信息
     *
     * @param error
     */
    public void showErrorAlert(String error) {

        Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.CLOSE);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("出错误了哟");
        alert.show();
    }

    /**
     * 从elements数组中获取string元素的序号
     *
     * @param elements
     * @param string
     * @return
     */
    public int getElemNum(Elem[] elements, String string) {
        for (int i = 0; i < elemCount; i++) {
            if (elements[i].getName().equals(string)) {
                return i;
            }
        }
        return -1;
    }

    public String makeShowText() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < relationCount; i++) {
            text.append("<").append(relationships[i].getFront()).append(",").append(relationships[i].getRear()).append(">").append("\n");
        }

        return text.toString();
    }

    public String makeGraphContent() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < relationCount; i++) {
            text.append(relations[i].getNum()).append(",")
                    .append(relations[i].getFront()).append(",").append(relations[i].getRear()).append("\n");
        }
        return text.toString();
    }

    public void showContent() {
        //输出看看
        for (int i = 0; i < elemCount; i++) {
            System.out.println(elements[i].toString());
        }
        //输出看看
        for (int i = 0; i < relationCount; i++) {
            System.out.println(relations[i].toString());
        }
        for (int i = 0; i < relationCount; i++) {
            System.out.println(relationships[i].toString());
        }
    }

    public void syncData() {
        MainController.N = relationCount;
        MainController.elemCount = elemCount;
        MainController.graphContent = makeGraphContent();
        for (int i = 0; i < elemCount; i++) {
            MainController.elements[i] = elements[i].getName();
        }

        for (int i = 0; i < relationCount; i++) {
            MainController.relationships[i].front = relationships[i].getFront();
            MainController.relationships[i].rear = relationships[i].getRear();
        }
    }

    public boolean isOneInput() {
        return textField1.getText().equals("") && !textField2.getText().equals("");
    }

    public boolean isTextOk() {
        if (textField2.getText().equals("") || textField2.getText().equals(textField1.getText())) {
            showErrorAlert("请确认输入内容是否正确");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断该元素是否已经在数组中了
     *
     * @param string 检测元素的字符串
     * @return 是否重复
     */
    public boolean isElemRepeat(String string) {
        for (int i = 0; i < elemCount; i++) {
            if (elements[i].getName().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRelationRepeat() {
        if (isOneInput()) {
            for (int i = 0; i < relationCount; i++) {
                if (relationships[i].getFront().equals("无")
                        && textField2.getText().equals(relationships[i].getRear())) {
                    showErrorAlert("关系已存在");
                    return true;
                }
            }
        } else {
            for (int i = 0; i < relationCount; i++) {
                if (textField1.getText().equals(relationships[i].getFront())
                        && textField2.getText().equals(relationships[i].getRear())) {
                    showErrorAlert("关系已存在");
                    return true;
                }
            }
        }

        return false;
    }

    //生成Stage时生成该Stage的Controller，Controller调用该方法把Stage传过来
    public void setStage(Stage stage) {
        //thisStage = stage;
    }
}
