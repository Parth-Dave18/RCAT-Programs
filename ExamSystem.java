package Learn;//package Swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
public class ExamSystem{
    JFrame home;
    JPanel panel;
    JPanel createTestPanel;
    JPanel giveTestPanel;
    JButton createTest, giveTest , finishCreateTest,addQues;

    JTextArea ques;
    JLabel quesLabel;
    JTextField [] options;
    JLabel [] optionsLabel;

    ButtonGroup buttonGroup;
    JRadioButton jbCheckBox;
    JRadioButton jbRadioButton;

    
    ExamSystem(){
        home = new JFrame("Exam System - Home");
        home.setSize(720,720);
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        home.setContentPane(panel);

        
        createTest = new JButton("Create Test");
        giveTest = new JButton("Give Test");

        createTest.setBounds(100,600,150,50);
        createTest.addActionListener((ActionEvent e)->{
            action(e);
        });
        panel.add(createTest);
        
        giveTest.setBounds(430,600,150,50);
        giveTest.addActionListener((ActionEvent e)->{
            action(e);
        });
        panel.add(giveTest);
       
    }
    
    public void action(ActionEvent e){
        if(e.getSource() == createTest){
            try {
//                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/examsystem","root","12345678");
                Statement stmt = conn.createStatement();

                //Set panel visibility to false
                panel.setVisible(false);
                //design createTestPanel
                designCreateTestPanel();


            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        }
        else if (e.getSource() == giveTest) {

        }
    }

    private void designCreateTestPanel() {

        createTestPanel = new JPanel();
        createTestPanel.setLayout(null);
        home.setContentPane(createTestPanel);
        createTestPanel.setVisible(true);

        ques = new JTextArea(1,5);
        createTestPanel.add(ques);
        ques.setLineWrap(true);
        ques.setWrapStyleWord(true);

        ques.setBounds(30,50,600,60);
        ques.setBorder(new EmptyBorder(5,5,5,5));

        quesLabel = new JLabel("Type Your Question Here");
        quesLabel.setBounds(30,25,300,20);
        createTestPanel.add(quesLabel);

        options = new JTextField[5];
        optionsLabel = new JLabel[5];

        for(int i = 0 ; i < 5 ; i++){
            options[i] = new JTextField();
            optionsLabel[i] = new JLabel();
            options[i].setBounds(30,170+i*50,500,30);
            optionsLabel[i].setBounds(30,150+i*50,300,20);
            optionsLabel[i].setText("Write Option " + (i+1) + " Here:");
            createTestPanel.add(optionsLabel[i]);
            createTestPanel.add(options[i]);
        }

        buttonGroup = new ButtonGroup();
        jbCheckBox = new JRadioButton("Multiple Correct Answer");
        jbRadioButton = new JRadioButton("Single Correct Answer");
        buttonGroup.add(jbRadioButton);
        buttonGroup.add(jbCheckBox);
        JLabel quesType = new JLabel("Type of Question");
        quesType.setBounds(30,430,200,30);
        createTestPanel.add(quesType);
        jbCheckBox.setBounds(30,470,200,20);
        jbRadioButton.setBounds(30,500,200,20);
        createTestPanel.add(jbCheckBox);
        createTestPanel.add(jbRadioButton);
        finishCreateTest = new JButton("Finish Create Test");
        finishCreateTest.setBounds(40,600,200,50);
        finishCreateTest.addActionListener((ActionEvent e)->{
            createTestPanelAction(e);
        });
        addQues = new JButton("Add another Question");
        addQues.setBounds(320,600,200,50);
        addQues.addActionListener((ActionEvent e)->{
            createTestPanelAction(e);
        });
        createTestPanel.add(finishCreateTest);
        createTestPanel.add(addQues);


    }

    private void createTestPanelAction(ActionEvent e) {
        if(isQuesFormatValid()){
            
            addQuesToDB();
            
            if(e.getSource() == finishCreateTest){
                
            }
            if(e.getSource() == addQues){
                
            }
        }
        else{

        }
    }

    private void addQuesToDB() {
    }

    private boolean isQuesFormatValid() {
        int countOption = 0;
        for(int i = 0 ; i < 5 ; i++){
            String str = options[i].getText().replaceAll("\\s","");
            if(!str.isEmpty()){
                countOption++;
            }
        }
        if((countOption>1)&&((jbCheckBox.isSelected())||(jbRadioButton.isSelected()))){
            return true;
        }
        else return false;
        
    }

    public static void main(String[] args) {
        new ExamSystem();
    }
}
