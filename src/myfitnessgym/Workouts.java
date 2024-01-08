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
public class Workouts extends JFrame {
    
    final private Font mainFont = new Font("Arial", Font.PLAIN, 20);
    JTextField tfWorkoutName;
    JTextField tfWorkoutDate;
    JTextField tfWorkoutDuration;
    JComboBox tfTrainerName;
    
    public void initialize(User user) {
        /************************* Info Panel *************************/
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Your workout history"));
        infoPanel.add(new JList(getAllWorkouts(user)));
        
        /************************* Form Panel *************************/
        JLabel lbWorkoutName = new JLabel("Workout Name");
        lbWorkoutName.setFont(mainFont);
        
        tfWorkoutName = new JTextField();
        tfWorkoutName.setFont(mainFont);
        
        JLabel lbWorkoutDate = new JLabel("Workout Date");
        lbWorkoutDate.setFont(mainFont);
        
        tfWorkoutDate = new JTextField();
        tfWorkoutDate.setFont(mainFont);
        
        JLabel lbWorkoutDuration = new JLabel("Workout Duration");
        lbWorkoutDuration.setFont(mainFont);
        
        tfWorkoutDuration = new JTextField();
        tfWorkoutDuration.setFont(mainFont);

        JLabel lbTrainerName = new JLabel("Trainer Name");
        lbTrainerName.setFont(mainFont);

        String trainerNames[] = getAllTrainerNames();
        tfTrainerName = new JComboBox(trainerNames);
        tfTrainerName.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30,50, 30, 50));
        formPanel.add(lbWorkoutName);
        formPanel.add(tfWorkoutName);
        formPanel.add(lbWorkoutDate);
        formPanel.add(tfWorkoutDate);
        formPanel.add(lbWorkoutDuration);
        formPanel.add(tfWorkoutDuration);
        formPanel.add(lbTrainerName);
        formPanel.add(tfTrainerName);


        /************************* Buttons Panel *************************/
        JButton btnAddWorkout = new JButton("Add Workout");
        btnAddWorkout.setFont(mainFont);
        btnAddWorkout.setForeground(Color.green);
        btnAddWorkout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String WorkoutName = tfWorkoutName.getText();
                String WorkoutDate = tfWorkoutDate.getText();
                String WorkoutDuration = tfWorkoutDuration.getText();
                String TrainerName = tfTrainerName.getItemAt(tfTrainerName.getSelectedIndex()) + "";

                if (WorkoutName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Workout Name");
                } else if (WorkoutDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Workout Date");
                } else if (WorkoutDuration.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Workout Duration");
                } else if (TrainerName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Trainer Name");
                } else {
                    addWorkout(user.id, WorkoutName, WorkoutDate, WorkoutDuration, TrainerName);
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
        buttonsPanel.add(btnAddWorkout);
        buttonsPanel.add(btnBack);


        /************************* Initialize the frame *************************/
        add(formPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Workouts");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 725);
        setMinimumSize(new Dimension(450, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private String[] getAllWorkouts(User user) {
        
        String[] allWorkouts = {};

        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Workout WHERE MemberId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allWorkouts = Arrays.copyOf(allWorkouts, allWorkouts.length + 1);
                allWorkouts[allWorkouts.length - 1] = resultSet.getString("WorkoutName") + "\t";
                allWorkouts[allWorkouts.length - 1] += resultSet.getString("WorkoutDuration") + "\t";
                allWorkouts[allWorkouts.length - 1] += resultSet.getString("WorkoutDate") + "\t";
                allWorkouts[allWorkouts.length - 1] += getTrainerName(resultSet.getInt("TrainerId")) + "\n";
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allWorkouts;
    }
    
    private String getTrainerName(int trainerId) {
        String trainerName = "";
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Trainer WHERE idTrainer = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, trainerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                trainerName = resultSet.getString("TrainerName");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return trainerName;
    }

    private String[] getAllTrainerNames() {
        String[] allTrainerNames = {};
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT TrainerName FROM Trainer";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allTrainerNames = Arrays.copyOf(allTrainerNames, allTrainerNames.length + 1);
                allTrainerNames[allTrainerNames.length - 1] = resultSet.getString("TrainerName");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allTrainerNames;
    }

    private void addWorkout(int memberId, String WorkoutName, String WorkoutDate, String WorkoutDuration, String TrainerName) {
        int trainerId = getTrainerId(TrainerName);
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "INSERT INTO Workout(WorkoutName,WorkoutDate,WorkoutDuration,MemberId,TrainerId)VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, WorkoutName);
            preparedStatement.setString(2, WorkoutDate);
            preparedStatement.setString(3, WorkoutDuration);
            preparedStatement.setInt(4, memberId);
            preparedStatement.setInt(5, trainerId);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private int getTrainerId(String TrainerName) {
        int trainerId = 0;
        
        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT idTrainer FROM Trainer WHERE TrainerName = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, TrainerName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                trainerId = resultSet.getInt("idTrainer");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return trainerId;
    }
    
}
