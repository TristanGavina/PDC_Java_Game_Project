/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;

/**
 *
 * @author trist
 */
public class MainGUI {
    Container con;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Game Project");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        frame.setLocationRelativeTo(null);
        StartMenu start = new StartMenu();
        frame.add(start);
        frame.setVisible(true);
    }
}
