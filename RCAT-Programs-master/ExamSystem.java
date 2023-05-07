//package Swing;
package Learn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.util.Random;

public class ExamSystem {
    public static final int RADIO_TYPE = 0;
    public static final int CHECKBOX_TYPE = 1;


    //Databse Connectivity
    Connection conn;
    Statement stmt;

    JFrame home;
    public int quesCount = 1;
    JFrame createTestFrame;
    JFrame giveTestFrame;

    JLabel testNameLabel;
    JTextField testName;
    JButton createTest, giveTest;

    JPanel panel;
    JPanel createTestPanel;
    JPanel giveTestPanel;
    JButton finishCreateTest, addQues;

    JTextArea ques;
    JLabel quesLabel;
    JTextField[] options;
    JLabel[] optionsLabel;
    JTextField corrAns;
    JLabel corrAnsLabel;
    ButtonGroup buttonGroup;
    JRadioButton jbCheckBox;
    JRadioButton jbRadioButton;
    private String testId;

    ExamSystem() {

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/examsystem", "root", "12345678");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        home = new JFrame("Exam System - Home");
        home.setSize(720, 720);
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        home.setContentPane(panel);

        createTest = new JButton("Create Test");
        giveTest = new JButton("Give Test");

        createTest.setBounds(100, 600, 150, 50);
        createTest.addActionListener((ActionEvent e) -> {
            action(e);
        });
        panel.add(createTest);

        testName = new JTextField();
        testName.setBounds(100,500,150,40);
        panel.add(testName);

        testNameLabel = new JLabel("Type Test Name Here: ");
        testNameLabel.setBounds(100,450,150,40);
        panel.add(testNameLabel);

        giveTest.setBounds(430, 600, 150, 50);
        giveTest.addActionListener((ActionEvent e) -> {
            action(e);
        });
        panel.add(giveTest);

    }

    public void action(ActionEvent e) {

        if (e.getSource() == createTest) {

            //Creating testList Table in DataBase
//            try {
//                stmt.execute("CREATE TABLE test_list (test_id varchar(50) PRIMARY KEY, test_name varchar(100) UNIQUE NOT NULL)");
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }

            //addTestInDB
            testId = createTestTable();


            //Set panel visibility to false
            panel.setVisible(false);
            //design createTestPanel
            designCreateTestPanel();

        }
        else if (e.getSource() == giveTest) {

        }
    }

    private String createTestTable() {
        String testId = getRandomId(10);
        String tstName = testName.getText();
        try {
            ResultSet rs = stmt.executeQuery("SELECT test_id FROM test_list WHERE test_id = '"+ testId +"'");
            if(rs.next()){
                return createTestTable();
            }
            else{
                stmt.execute("CREATE TABLE "+ testId + " (Qno int, ques varchar(5000),op1 varchar(500),op2 varchar(500),op3 varchar(500),op4 varchar(500),op5 varchar(500),corrOp int,qtype int)");
                stmt.execute("INSERT INTO test_list VALUES ("+testId+","+tstName+")");
                return testId;
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error! This Test Name is already Taken!\nWrite a another name");
            throw new RuntimeException(e);
        }
    }

    private void designCreateTestPanel() {

        createTestPanel = new JPanel();
        createTestPanel.setLayout(null);
        home.setContentPane(createTestPanel);
        createTestPanel.setVisible(true);

        ques = new JTextArea();
        createTestPanel.add(ques);

        ques.setLineWrap(true);
        ques.setWrapStyleWord(true);

        ques.setBounds(30, 50, 600, 60);
        ques.setBorder(new EmptyBorder(5, 5, 5, 5));

        quesLabel = new JLabel("Type Your Question "+quesCount+".  Here");
        quesLabel.setBounds(30, 25, 300, 20);
        createTestPanel.add(quesLabel);

        options = new JTextField[5];
        optionsLabel = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            options[i] = new JTextField();
            optionsLabel[i] = new JLabel();
            options[i].setBounds(30, 170 + i * 50, 500, 30);
            optionsLabel[i].setBounds(30, 150 + i * 50, 300, 20);
            optionsLabel[i].setText("Write Option " + (i + 1) + " Here:");
            createTestPanel.add(optionsLabel[i]);
            createTestPanel.add(options[i]);
        }

        buttonGroup = new ButtonGroup();
        jbCheckBox = new JRadioButton("Multiple Correct Answer");
        jbRadioButton = new JRadioButton("Single Correct Answer");
        buttonGroup.add(jbRadioButton);
        buttonGroup.add(jbCheckBox);
        JLabel quesType = new JLabel("Type of Question");
        quesType.setBounds(30, 430, 200, 30);
        createTestPanel.add(quesType);
        jbCheckBox.setBounds(30, 470, 200, 20);
        jbRadioButton.setBounds(30, 500, 200, 20);
        createTestPanel.add(jbCheckBox);
        createTestPanel.add(jbRadioButton);
        finishCreateTest = new JButton("Finish Create Test");
        finishCreateTest.setBounds(40, 600, 200, 50);
        finishCreateTest.addActionListener((ActionEvent e) -> {
            createTestPanelAction(e);
        });
        addQues = new JButton("Add another Question");
        addQues.setBounds(320, 600, 200, 50);
        addQues.addActionListener((ActionEvent e) -> {
            createTestPanelAction(e);
        });

        corrAnsLabel = new JLabel("Type Correct Option (1,2,3,4,5)");
        corrAnsLabel.setBounds(320, 430, 200, 30);
        createTestPanel.add(corrAnsLabel);

        corrAns = new JTextField();
        corrAns.setBounds(320, 470, 200, 30);
        createTestPanel.add(corrAns);

        createTestPanel.add(finishCreateTest);
        createTestPanel.add(addQues);

    }

    private void createTestPanelAction(ActionEvent e) {
        if (isQuesFormatValid()) {
            addQuesToDB();

            if (e.getSource() == finishCreateTest) {

            }
            if (e.getSource() == addQues) {

            }
        } else {

        }
    }

    private void addQuesToDB() {
        try {
            String question = ques.getText();
            String op[] =  new String[5];
            for(int i = 0 ; i < 5 ; i ++){
                op[i] = options[i].getText();
            }
            int corrOp = Integer.parseInt(corrAns.getText());
            int quType = 0;
            if(jbCheckBox.isSelected()){
                quType = 1;
            }
            stmt.execute("INSERT INTO " +
                   testId + " VALUES(" +
                    quesCount +"," + question +","+op[0]+","+op[1]+","+op[2]+","+op[3]+","+op[4]+","+corrOp+","+quType+")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRandomId(int len){
        Random random = new Random();
        final String alpha = "abcdefghijklmnopqrstuvwxyz";
        final String alphaNum = alpha+"0123456789";
        String str = ""+alpha.charAt(random.nextInt(alpha.length()));
        for(int i = 1 ; i < len ; i++){
            int randomIndex = random.nextInt(alphaNum.length());
            str = str + alphaNum.charAt(randomIndex);
        }
        return str;
    }

    private boolean isQuesFormatValid() {
        int countOption = 0;
        for (int i = 0; i < 5; i++) {
            String str = options[i].getText().replaceAll("\\s", "");
            if (!str.isEmpty()) {
                countOption++;
            }
        }
        if (!corrAns.getText().replaceAll("\\s", "").isEmpty()) {
            int x = -1;
            try{
            x = Integer.parseInt(corrAns.getText());
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null,"Please Enter a valid Integer value in Correct option text field!!");
            }
            
            if (x >= 1 && x <= 5) {
                if ((countOption > 1) && ((jbCheckBox.isSelected()) || (jbRadioButton.isSelected()))) {
                    return true;
                } else {
                    return false;
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Please Enter a correct Option in Integer between 1 to 5");
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Correct Answer Does not Entered.\nPlease enter a correct option!!");
            return false;
        }

    }

    public static void main(String[] args) {
        new ExamSystem();
    }
}
