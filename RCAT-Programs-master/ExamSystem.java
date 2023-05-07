package Swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ExamSystem{
    JFrame home;
    JButton createTest, giveTest;
    
    ExamSystem(){
        home = new JFrame("Exam System - Home");
        home.setSize(720,720);
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createTest = new JButton("Create Test");
        giveTest = new JButton("Give Test");
        
        createTest.setBounds(100,600,150,50);
        createTest.addActionListener((ActionEvent e)->{
            action();
        });
        home.add(createTest);
        
        giveTest.setBounds(430,600,150,50);
        giveTest.addActionListener((ActionEvent e)->{
            action();
        });
        home.add(giveTest);
       
    }
    
    public void action(){
        
    }
    public static void main(String[] args) {
        new ExamSystem();
    }
}