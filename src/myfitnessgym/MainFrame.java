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
import java.sql.*;

import javax.swing.*;

public class MainFrame extends JFrame {
    
    final private Font mainFont = new Font("Arial", Font.PLAIN, 20);
    
    public void initialize(User user) {
        /***************************** Info Panel *****************************/
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Name"));
        infoPanel.add(new JLabel(user.name));
        infoPanel.add(new JLabel("Email"));
        infoPanel.add(new JLabel(user.email));
        infoPanel.add(new JLabel("Phone"));
        infoPanel.add(new JLabel(user.phone));
        infoPanel.add(new JLabel("Address"));
        infoPanel.add(new JLabel(user.address));
        infoPanel.add(new JLabel("Date of Birth"));
        infoPanel.add(new JLabel(user.DOB));
        
        /************************* Buttons Panel *************************/
        JButton btnPlans = new JButton("Plans");
        btnPlans.setFont(mainFont);
        btnPlans.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
                Plan plan = getUserPlan(user);
                user.plan = plan;
                
                Plans plansForm = new Plans();
                plansForm.initialize(user);
                dispose();
            }
                
        });
        
        JButton btnAttendance = new JButton("Attendance");
        btnAttendance.setFont(mainFont);
        btnAttendance.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Attendances attendanceForm = new Attendances();
                attendanceForm.initialize(user);
                dispose();
            }
                
        });
        
        JButton btnWorkouts = new JButton("Workouts");
        btnWorkouts.setFont(mainFont);
        btnWorkouts.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Workouts workoutsForm = new Workouts();
                workoutsForm.initialize(user);
                dispose();
            }
                
        });
        
        JButton btnEquipment = new JButton("Equipment");
        btnEquipment.setFont(mainFont);
        btnEquipment.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Equipment equipmentForm = new Equipment();
                equipmentForm.initialize(user);
                dispose();
            }
                
        });
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(mainFont);
        btnLogout.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MyFitnessGym loginForm = new MyFitnessGym();
                loginForm.initialize();
                dispose();
            }
                
        });
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 1, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnPlans);
        buttonsPanel.add(btnAttendance);
        buttonsPanel.add(btnWorkouts);
        buttonsPanel.add(btnEquipment);
        buttonsPanel.add(btnLogout);
        
        
        /************************* Initialize the frame *************************/
        add(infoPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);


        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private Plan getUserPlan(User user) {
        Plan plan = null;
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Plan WHERE MemberId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                plan = new Plan();
                plan.name = resultSet.getString("PlanName");
                plan.startDate = resultSet.getString("StartDate");
                plan.endDate = resultSet.getString("EndDate");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return plan;
    }
}
