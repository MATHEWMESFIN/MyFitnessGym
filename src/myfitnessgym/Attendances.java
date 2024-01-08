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
import java.util.Arrays;

import javax.swing.*;

/**
 *
 * @author mathewmesfin
 */
public class Attendances extends JFrame{
    
    final private Font mainFont = new Font("Arial", Font.PLAIN, 20);
    JComboBox tfClassName;
    JTextField tfAttendanceDate;
    
    public void initialize(User user) {
        /************************* Info Panel *************************/
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Your attendance"));
        infoPanel.add(new JList(getAllAttendance(user)));
        
        /************************* Form Panel *************************/
        JLabel lbClassName = new JLabel("Class Name");
        lbClassName.setFont(mainFont);
        
        String plans[] = getAllClassNames();
        tfClassName = new JComboBox(plans);
        tfClassName.setFont(mainFont);
        
        JLabel lbAttendanceDate = new JLabel("Attendance Date");
        lbAttendanceDate.setFont(mainFont);
        
        tfAttendanceDate = new JTextField();
        tfAttendanceDate.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30,50, 30, 50));
        formPanel.add(lbClassName);
        formPanel.add(tfClassName);
        formPanel.add(lbAttendanceDate);
        formPanel.add(tfAttendanceDate);


        /************************* Buttons Panel *************************/
        JButton btnAddAttendance = new JButton("Add Attendance");
        btnAddAttendance.setFont(mainFont);
        btnAddAttendance.setForeground(Color.green);
        btnAddAttendance.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String ClassName = tfClassName.getItemAt(tfClassName.getSelectedIndex()) + "";
                String AttendanceDate = tfAttendanceDate.getText();

                if (ClassName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Class Name");
                } else if (AttendanceDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter an Attendance Date");
                } else {
                    addAttendance(user.id, ClassName, AttendanceDate);
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
        buttonsPanel.add(btnAddAttendance);
        buttonsPanel.add(btnBack);


        /************************* Initialize the frame *************************/
        add(formPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Attendance");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 725);
        setMinimumSize(new Dimension(450, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private String[] getAllAttendance(User user) {
        
        String[] allAttendance = {};

        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Attendance WHERE MemberId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allAttendance = Arrays.copyOf(allAttendance, allAttendance.length + 1);
                allAttendance[allAttendance.length - 1] = resultSet.getString("AttendanceDate") + "\t" + getClassName(resultSet.getInt("ClassId"));
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allAttendance;

    }
    
    private String getClassName(int classId) {
        String className = "";
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Class WHERE idClass = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, classId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                className = resultSet.getString("ClassName");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return className;
    }
    
    private String[] getAllClassNames() {
        String[] allClassNames = {};

        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT ClassName FROM Class";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            // add all class names to the array
            while (resultSet.next()) {
                allClassNames = Arrays.copyOf(allClassNames, allClassNames.length + 1);
                allClassNames[allClassNames.length - 1] = resultSet.getString("ClassName");
            }
            

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allClassNames;
    }
    
    private void addAttendance(int MemberId, String ClassName, String AttendanceDate) {
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "INSERT INTO Attendance(AttendanceDate,Classid,MemberId)VALUES(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, AttendanceDate);
            preparedStatement.setInt(2, getClassId(ClassName));
            preparedStatement.setInt(3, MemberId);
            
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private int getClassId(String ClassName) {
        int classId = 0;

        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT idClass FROM Class WHERE ClassName = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ClassName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                classId = resultSet.getInt("idClass");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return classId;

    }
}
