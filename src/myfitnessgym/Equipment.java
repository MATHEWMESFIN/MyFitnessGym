/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfitnessgym;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
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
public class Equipment extends JFrame {
    
    final private Font mainFont = new Font("Arial", Font.PLAIN, 20);
    
    public void initialize(User user) {
        /************************* Info Panel *************************/
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Equipment"));
        infoPanel.add(new JList(getAllEquipment(user)));

        /************************* Buttons Panel *************************/
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
        buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnBack);

        /************************* Initialize the frame *************************/
        add(infoPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Equipment");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 725);
        setMinimumSize(new Dimension(450, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private String[] getAllEquipment(User user) {
        String[] allEquipment = {};

        final String DB_URL = "jdbc:mysql://localhost:3306/project?zeroDateTimeBehavior=CONVERT_TO_NULL";
        final String USERNAME = "root";
        final String PASSWORD = "3macRock$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM Equipment";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // add to allEquipment
                allEquipment = Arrays.copyOf(allEquipment, allEquipment.length + 1);
                allEquipment[allEquipment.length - 1] = resultSet.getString("EquipmentName") + "\t" + resultSet.getString("EquipmentCondition");

                // allEquipment += resultSet.getString("EquipmentName") + "\t";
                // allEquipment += resultSet.getString("EquipmentCondition") + "\n";
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allEquipment;
    }
    
}
