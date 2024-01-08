/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfitnessgym;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

/**
 *
 * @author mathewmesfin
 */
public class Plans extends JFrame{
    
    final private Font mainFont = new Font("Arial", Font.PLAIN, 20);
    JComboBox tfPlanName;
    JTextField tfStartDate;
    JTextField tfEndDate;
    
    public void initialize(User user) {
        /***************************** Info Panel *****************************/
        
        JPanel infoPanel = new JPanel();
        // if this user has a plan
        if (user.plan != null) {
            infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
            infoPanel.add(new JLabel("Current Plan Name"));
            infoPanel.add(new JLabel(user.plan.name));
            infoPanel.add(new JLabel("Start date"));
            infoPanel.add(new JLabel(user.plan.startDate));
            infoPanel.add(new JLabel("End date"));
            infoPanel.add(new JLabel(user.plan.endDate));
        }
        
        /************************* Form Panel *************************/
        JLabel lbPlanName = new JLabel("New Plan Name");
        lbPlanName.setFont(mainFont);
        
        String plans[] = {"Lose Weight", "Build Muscle", "Build Endurance"};
        tfPlanName = new JComboBox(plans);
        tfPlanName.setFont(mainFont);
        
        JLabel lbStartDate = new JLabel("Start Date");
        lbStartDate.setFont(mainFont);
        
        tfStartDate = new JTextField();
        tfStartDate.setFont(mainFont);
        
        JLabel lbEndDate = new JLabel("End Date");
        lbEndDate.setFont(mainFont);
        
        tfEndDate = new JTextField();
        tfEndDate.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30,50, 30, 50));
        formPanel.add(lbPlanName);
        formPanel.add(tfPlanName);
        formPanel.add(lbStartDate);
        formPanel.add(tfStartDate);
        formPanel.add(lbEndDate);
        formPanel.add(tfEndDate);

        /************************* Buttons Panel *************************/
        JButton btnReplacePlan = new JButton("New Plan");
        btnReplacePlan.setFont(mainFont);
        btnReplacePlan.setForeground(Color.green);
        btnReplacePlan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String PlanName = tfPlanName.getItemAt(tfPlanName.getSelectedIndex()) + "";
                String StartDate = tfStartDate.getText();
                String EndDate = tfEndDate.getText();

                if (PlanName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Plan Name");
                } else if (StartDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Start Date");
                } else if (EndDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter an End Date");
                } else {
                    if (user.plan != null) {
                        user.plan = replacePlan(user, PlanName, StartDate, EndDate);
                    } else {
                        user.plan = addPlan(user, PlanName, StartDate, EndDate);
                    }
                    
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                }
            }
            
        });
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(mainFont);
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MainFrame mainFrame = new MainFrame();
                mainFrame.initialize(user);
                dispose();
            }
            
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnReplacePlan);
        buttonsPanel.add(btnBack);


        /************************* Initialize the frame *************************/
        add(formPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Plans");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setMinimumSize(new Dimension(450, 500));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private Plan replacePlan(User user, String PlanName, String StartDate, String EndDate) {
        
        Plan plan = null;
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "UPDATE Plan SET StartDate = ?, EndDate = ?, PlanName = ? WHERE MemberId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, StartDate);
            preparedStatement.setString(2, EndDate);
            preparedStatement.setString(3, PlanName);
            preparedStatement.setInt(4, user.id);
            
            preparedStatement.execute();

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return plan;
    }
    
    private Plan addPlan(User user, String PlanName, String StartDate, String EndDate) {
        Plan plan = null;
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "INSERT INTO Plan(StartDate,EndDate,PlanName,MemberId)VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, StartDate);
            preparedStatement.setString(2, EndDate);
            preparedStatement.setString(3, PlanName);
            preparedStatement.setInt(4, user.id);
            
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return plan;
    }
        
}
