/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Swing;

import java.sql.*;
import java.io.*;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;

public class ChatBot extends JFrame {

    JPanel contentPane;
    JTextField textField_1;
    JButton btnSend;
    String main = "";
    JTextArea textArea;
    JScrollBar scrollBar;

    ChatBot() {
        setTitle("R-CAT AI ChatBot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 428, 657);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        textField_1 = new JTextField();
        textField_1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    action();
                }
            }
        });
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
        textField_1.setBounds(0, 555, 338, 60);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        btnSend = new JButton("Send");
        btnSend.addActionListener((ActionEvent e) -> {
            action();
        });
        btnSend.setBounds(341, 555, 75, 59);
        contentPane.add(btnSend);
        textArea = new JTextArea();
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 19));
        textArea.setEditable(false);
        textArea.setBounds(0, 0, 396, 554);
        contentPane.add(textArea);
    }

    public void action() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatbotdb", "root", "12345678");
            Statement stmt = conn.createStatement();

            String quer = textField_1.getText();
            textArea.append(" You-> " + quer + "\n");
            quer = quer.toLowerCase();

            ResultSet res = stmt.executeQuery("select * from queries where ques = '" + quer + "'");
            int id = 0;
            while (res.next()) {
//                System.out.println(res.getInt(1) + "  " + res.getString(2) + "  " + res.getString(3));
                id = res.getInt(1);
            }
            Random rand = new Random();
            int randNum = rand.nextInt(5);
            ResultSet reply = stmt.executeQuery("select * from replies where id = " + id + " and count = " + randNum);

            while (reply.next()) {
//                System.out.println(res.getInt(1) + "  " + res.getString(2) + "  " + res.getString(3));
                String ans = reply.getString(3);
                ai(ans);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    void ai(String s) {
        textArea.append(" AI->" + s + "\n\n");
        textField_1.setText("");
    }

    public static void main(String[] args) {
        try {
            ChatBot frame = new ChatBot();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
