/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfitnessgym;

/**
 *
 * @author mathewmesfin
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.*;

public class Registration extends JFrame {
    
    final private Font mainFont = new Font("Arial", Font.PLAIN, 20);
    JTextField tfName;
    JTextField tfEmail;
    JPasswordField pfPassword;
    JTextField tfPhone;
    JTextField tfAddress;
    JTextField tfDOB;
    
    public void initialize() {
        /************************* Form Panel *************************/
        JLabel lbRegistrationForm = new JLabel("Regisration Form", SwingConstants.CENTER);
        lbRegistrationForm.setFont(mainFont);

        JLabel lbName = new JLabel("Name");
        lbName.setFont(mainFont);
        
        tfName = new JTextField();
        tfName.setFont(mainFont);
        
        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);
        
        JLabel lbPhone = new JLabel("Phone Number");
        lbPhone.setFont(mainFont);

        tfPhone = new JTextField();
        tfPhone.setFont(mainFont);
        
        JLabel lbAddress = new JLabel("Address");
        lbAddress.setFont(mainFont);

        tfAddress = new JTextField();
        tfAddress.setFont(mainFont);
        
        JLabel lbDOB = new JLabel("Date of Birth");
        lbDOB.setFont(mainFont);

        tfDOB = new JTextField();
        tfDOB.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30,50, 30, 50));
        formPanel.add(lbRegistrationForm);
        formPanel.add(lbName);
        formPanel.add(tfName);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);
        formPanel.add(lbPhone);
        formPanel.add(tfPhone);
        formPanel.add(lbAddress);
        formPanel.add(tfAddress);
        formPanel.add(lbDOB);
        formPanel.add(tfDOB);

        /************************* Buttons Panel *************************/
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(mainFont);
        btnRegister.setForeground(Color.green);
        btnRegister.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String Name = tfName.getText();
                String Email = tfEmail.getText();
                String Password = String.valueOf(pfPassword.getPassword());
                String Phone = tfPhone.getText();
                String Address = tfAddress.getText();
                String DOB = tfDOB.getText();

                if (Name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Name");
                } else if (Email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter an Email");
                } else if (Password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Password");
                } else if (Phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Phone Number");
                } else if (Address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter an Address");
                } else if (DOB.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Date of Birth");
                } else {
                    registerUser(Name, Email, Password, Phone, Address, DOB);
                    MyFitnessGym loginForm = new MyFitnessGym();
                    loginForm.initialize();
                    dispose();
                }
            }
            
        });

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MyFitnessGym loginForm = new MyFitnessGym();
                loginForm.initialize();
                dispose();
            }
            
        });
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnRegister);
        buttonsPanel.add(btnLogin);
        buttonsPanel.add(btnCancel);


        /************************* Initialize the frame *************************/
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Registration Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 725);
        setMinimumSize(new Dimension(450, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void registerUser(String Name, String Email, String Password, String Phone, String Address, String DOB) {
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";
            
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "INSERT INTO Member(MemberName,MemberEmail,MemberPhone,MemberAddress,MemberPassword,MemberDOB)VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,Name);
            pst.setString(2,Email);
            pst.setString(3,Phone);
            pst.setString(4,Address);
            pst.setString(5,Password);
            pst.setString(6,DOB);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed");
            }

            pst.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
}
