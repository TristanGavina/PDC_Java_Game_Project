
import javax.swing.*;
import java.awt.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trist
 */
public class PlayerEnemyGUI extends JFrame {
    private JLabel enemyLabel;

    public PlayerEnemyGUI(String enemyType) {
        
        setTitle("Player vs Enemy");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Load player image
        ImageIcon playerIcon = new ImageIcon("./Images/player.png");
        JLabel playerLabel = new JLabel(playerIcon);
        add(playerLabel);

        // Load enemy image dynamically
        ImageIcon enemyIcon = getEnemyIcon(enemyType);
        enemyLabel = new JLabel(enemyIcon);
        add(enemyLabel);

        setVisible(true);
    }

    private ImageIcon getEnemyIcon(String enemyType) {
        String path = "./Images/" + enemyType.toLowerCase() + ".png";
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            System.out.println("Image not found for: " + enemyType);
            return new ImageIcon(); // return empty icon
        }
        return new ImageIcon(path);
    }

    public static void main(String[] args) {
        // Example: enemy is either "monkey" or "demon"
        SwingUtilities.invokeLater(() -> new PlayerEnemyGUI("demon"));
    }
}
