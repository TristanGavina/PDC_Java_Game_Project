/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.CombatManager;
import com.mycompany.java_game_project.Encounter;
import com.mycompany.java_game_project.Enemy;
import com.mycompany.java_game_project.EnemyPointCalc;
import com.mycompany.java_game_project.EnemyType;
import com.mycompany.java_game_project.Interfaces.CombatListeners;
import com.mycompany.java_game_project.Interfaces.ICalculatePoint;
import com.mycompany.java_game_project.Interfaces.UpdateUI;
import com.mycompany.java_game_project.Java_Game_Project;
import com.mycompany.java_game_project.Player;
import com.mycompany.java_game_project.StageManager;
import com.mycompany.java_game_project.StyleCombatUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * This class is gui for both combat/encounter and resting
 * @author trist
 */
public class CombatGUI extends JPanel implements CombatListeners,  UpdateUI{
     
    private StageManager stageManager;
    private CombatManager combatManager;
    private ICalculatePoint calcPoint;
    private StyleCombatUI styleUI;
    
    //UI stuff
    private Image background;
    private JTextArea combatLog;
    private JLabel stageCount;
    private StatsPanel playerStatPanel, enemyStatPanel;
            
    //combat button
    private JButton attack, defend, heal, detail, quit;
    
    //rest button
    private JButton cont, rest, quit2;
    
    private JPanel currentBottomPanel;
    private Player player;
    private Java_Game_Project frame;
    
    public CombatGUI(Java_Game_Project frame, Player player, Encounter encounter){
        this.frame = frame;
        this.player = player;
        this.stageManager = new StageManager();
        this.combatManager = new CombatManager(player);
        this.calcPoint = new EnemyPointCalc();
        this.styleUI = new StyleCombatUI();
        
        //listeners
        this.combatManager.addCombatListeners(this);
        
        //UI components
        this.background = new ImageIcon("./Images/combat.jpg").getImage();
        this.combatLog = styleUI.showCombatLog();
        this.stageCount = makeStageLabel();
        this.playerStatPanel = new StatsPanel();
        this.enemyStatPanel = new StatsPanel();
        
        //create buttons
        this.attack = styleUI.styleButton("ATTACK");
        this.defend = styleUI.styleButton("DEFEND");
        this.heal = styleUI.styleButton("HEAL");
        this.detail = styleUI.styleButton("DETAILS");
        this.quit = styleUI.styleButton("QUIT");
        
        this.cont = styleUI.styleButton("CONTINUE");
        this.rest = styleUI.styleButton("REST");
        this.quit2 = styleUI.styleButton("QUIT");
        
        initUI();
        eventListeners();
        nextFight();
    }
    
    public void initUI(){
        setLayout(new BorderLayout());
        
        //Stage counter setup
        JPanel stagePanel = new JPanel();
        stagePanel.setOpaque(false);
        stagePanel.setLayout(new BoxLayout(stagePanel, BoxLayout.Y_AXIS));
        stageCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        stagePanel.add(stageCount);
        add(stagePanel, BorderLayout.NORTH);
        
        //combat log
        JScrollPane logPane = new JScrollPane(combatLog);
        logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPane.setOpaque(false);
        logPane.getViewport().setOpaque(false);
        
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setOpaque(false);
        logPanel.add(logPane, BorderLayout.CENTER);
        add(logPanel, BorderLayout.CENTER);
        
        //styling stats panel
        styleUI.styleStatPanel(playerStatPanel);
        styleUI.styleStatPanel(enemyStatPanel);
        playerStatPanel.setOpaque(false);
        enemyStatPanel.setOpaque(false);
        
        //show stats
        updateStats();
        
        //combatInterface;
        combatInterface();
    }

    private void eventListeners(){
        attack.addActionListener(e -> combatManager.doAttack());
        defend.addActionListener(e -> combatManager.doDefend());
        heal.addActionListener(e -> combatManager.doHeal());
        detail.addActionListener(e -> showDetails());
        quit.addActionListener(e -> quitGame());
        
        cont.addActionListener(e -> nextFight());
        rest.addActionListener(e -> playerRest());
        quit2.addActionListener(e -> quitGame());
    }
    
    // making stage label
    private JLabel makeStageLabel(){
        JLabel stageLabel = new JLabel("Current stage: STAGE " + stageManager.getCurrentStage());
        stageLabel.setForeground(Color.WHITE);
        stageLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        stageLabel.setBorder(border);
        return stageLabel;
    }
    
    private void combatInterface(){
        if (currentBottomPanel != null) {
            remove(currentBottomPanel);
        }
        
        //button panel
        JPanel buttonPosition = new JPanel(new GridLayout(3,2, 5, 5));
        buttonPosition.setOpaque(false);
        buttonPosition.add(attack);
        buttonPosition.add(defend);
        buttonPosition.add(heal);
        buttonPosition.add(detail);
        buttonPosition.add(quit);
        buttonPosition.add(new JLabel());
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,10));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonPosition);
        
        //stats panel
        JPanel statPanel = new JPanel(new BorderLayout());
        statPanel.setOpaque(false);
        statPanel.add(playerStatPanel, BorderLayout.WEST);
        statPanel.add(enemyStatPanel, BorderLayout.EAST);
        
        currentBottomPanel = new JPanel(new BorderLayout());
        currentBottomPanel.setOpaque(false);
        currentBottomPanel.add(statPanel, BorderLayout.CENTER);
        currentBottomPanel.add(buttonWrapper, BorderLayout.EAST);

        add(currentBottomPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    
    private void showRestingPanel(){
        //removing existing bottom panel
        if (currentBottomPanel != null) {
            remove(currentBottomPanel);
        }
        
        JPanel buttonPosition = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPosition.setOpaque(false);
        buttonPosition.add(cont);
        buttonPosition.add(rest);
        buttonPosition.add(quit2);
        buttonPosition.add(new JLabel());
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonPosition);
        
        currentBottomPanel = buttonWrapper;
        add(currentBottomPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    
    private void showDetails(){
        String details = "=== GAME DETAILS ===\n" +
                        "Stage: " + stageManager.getCurrentStage() + "\n" +
                        "Enemies Defeated: " + stageManager.getEnemiesDefeated() + "\n" +
                        "Player: " + player.getName() + "\n" +
                        "Player HP: " + player.getHealth() + "/" + player.getMaxHP() + "\n" +
                        "Player Attack: " + player.getAttack() + "\n" +
                        "Player Defense: " + player.getDefense() + "\n" +
                        "Player Points: " + calcPoint.getTotalScore() + "\n";
        
        JOptionPane.showMessageDialog(this, details, "Game Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void nextFight(){
        clearCombatLog();
        
        if(stageManager.gameCompleted()){
            gameFinish();
            return;
        }
        
        EnemyType nextType = stageManager.getNextEnemy();
        if(nextType == null){
            stageManager.nextStage();
            showCombatLog(" STAGE " + stageManager.getCurrentStage() + " COMPLETED");
            showCombatLog("Moving to next stage...");
            nextFight();
            return;
        }
        
        Enemy nextEnemy = new Enemy(nextType);
        combatManager.startCombat(nextEnemy);
        showCombatLog(player.getName() + " encountered a " + nextEnemy.getType() + " !");
        updateStats();
        combatInterface();
    }
    
    private void playerRest(){
        player.healToFull();
        showCombatLog(player.getName() + " recovered to full HP after resting...");
        updateStats();
        nextFight();
    }
    
    private void quitGame(){
        int choice = JOptionPane.showConfirmDialog(this,
                "Do you want to Save and Quit?",
                "SAVE AND QUIT",
                JOptionPane.YES_NO_OPTION
                );
        
        if(choice == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    private void gameFinish(){
        JOptionPane.showMessageDialog(this,
                "    CONGRATULATIONS ON BEATING THE GAME!!!   ",
                "GAME COMPLETE",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    @Override
    public void updateStats(){
        if(player != null){
            playerStatPanel.playerStat(player);
        }
        
        if(combatManager.getCurrentEnemy() != null){
            enemyStatPanel.enemyStat(combatManager.getCurrentEnemy());
        }
        
        stageCount.setText("Current stage: STAGE " + stageManager.getCurrentStage());
    }
    
    @Override
    public void showCombatLog(String msg){
        SwingUtilities.invokeLater(() -> {
            combatLog.append(msg + "\n");
            combatLog.setCaretPosition(combatLog.getDocument().getLength());
        });
    }
    
    @Override
    public void clearCombatLog(){
        combatLog.setText("");
    }
    
    @Override
    public void enemyDefeated(Enemy enemy){
        int points = calcPoint.calcPoints(enemy.getType());
        calcPoint.pointAdded(points);
        showCombatLog(enemy.getType() + " has been defeated!");
        showCombatLog(player.getName() + " earned " + points + " points! Your total score is now: " + calcPoint.getTotalScore());
        
        stageManager.nextFight();
        
        if(stageManager.stageCompleted()){
            showCombatLog("STAGE " + stageManager.getCurrentStage() + " COMPLETED!");
            stageManager.nextStage();
            //showCombatLog("Mowing to next stage.... \n STAGE " + stageManager.getCurrentStage());
        }
        
        showRestingPanel();
    }
    
    @Override
    public void playerDefeated(){
        showCombatLog(player.getName() + " has been DEFEATED!");
        JOptionPane.showMessageDialog(
            this, 
            "You have been defeated",
            "GAME OVER!!!",
            JOptionPane.ERROR_MESSAGE
        );
        System.exit(0);
    }
    
    @Override
    public void stageComplete(int stage){
        showCombatLog("STAGE  " + stage + " COMPLETE!!!");
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, getWidth(), getHeight(), this);
    }
}
