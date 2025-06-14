/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Encounter;
import com.mycompany.java_game_project.Enemy;
import com.mycompany.java_game_project.EnemyType;
import com.mycompany.java_game_project.Java_Game_Project;
import com.mycompany.java_game_project.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * This class is gui for both combat/encounter and resting
 * @author trist
 */
public class CombatGUI extends JPanel {
    private Image image;
    private Font stageFont = new Font("Times New Roman", Font.BOLD, 25);
    private Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);
    private Font combatFont = new Font("Times New Roman", Font.BOLD, 15);
    private JButton attack, defend, heal, detail, quit;
    private JButton cont, rest, quit2;
    private Dimension buttonSize = new Dimension(150, 40);
    private JTextArea combatLog;
    private JLabel stageCount, playerStat, enemyStat;
    
    private Player player;
    private Enemy currentEnemy;
    private Encounter encounter;
    private boolean inCombat = false;
    private boolean phase = true;
    
    private int currentStage = 1;
    private int enemyDefeat = 0;

    private Java_Game_Project frame;
    
    public CombatGUI(Java_Game_Project frame, Player player, Encounter encounter){
        this.frame = frame;
        this.player = player;
        this.encounter = encounter;
        this.image = new ImageIcon("./Images/combat.jpg").getImage();
        showUI();
        startNextCombat();
    }
    
    public void showUI(){
        setLayout(new BorderLayout());
        
        /**
         * very top left panel for stage
         * 
         * bottom panel for user/player
         * south/very bottom panel for player action
         * bottom right (above player action) player rest UI (only if player defeated enemy
         * bottom left-right (right of player model) show player stats
         * 
         * inside panel for player action will be action buttons (atk/heal/def/details/quit)
         * 
         * top right enemy image
         * top right-right (right of enemy) show stats (atk/def/hp)
         * 
         */
        
        //Stage counter setup
        stageCount = new JLabel("Current stage: STAGE " + currentStage);
        stageCount.setForeground(Color.WHITE);
        stageCount.setFont(stageFont);
        Border border = BorderFactory.createLineBorder(Color.RED, 8);
        stageCount.setBorder(border);
        
        JPanel stagePanel = new JPanel();
        stagePanel.setOpaque(false);
        stagePanel.setLayout(new BoxLayout(stagePanel, BoxLayout.Y_AXIS));
        //stagePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        stageCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        stagePanel.add(stageCount, BorderLayout.CENTER);
        add(stagePanel, BorderLayout.NORTH);
        
        //shows stats
        showStats();
        //combat/action buttons
        combatButtons();
        //resting menu
        restingButtons();
        //combatLog
        combatLog();
        
        //combatInterface;
        combatInterface();
    }
    
    private void showStats(){
        //show player stat
        playerStat = new JLabel();
        playerStat.setForeground(Color.WHITE);
        playerStat.setFont(combatFont);
        playerStat.setVerticalAlignment(JLabel.TOP);
        
        //show enemy stat
        enemyStat = new JLabel();
        enemyStat.setForeground(Color.WHITE);
        enemyStat.setFont(combatFont);
        enemyStat.setVerticalAlignment(JLabel.TOP);
        
        updateStats();
    }
    
    private void combatButtons(){
        //ACTION BUTTONS
        attack = new JButton("ATTACK");
        defend = new JButton("DEFEND");
        heal = new JButton("HEAL");
        detail = new JButton("DETAILS");
        quit = new JButton("QUIT");
                
        JButton[] buttons = {attack, defend, heal, detail, quit};
        for (JButton button : buttons){
            buttonStyle(button);
        }
        
        //action listeners
        attack.addActionListener(e -> doAttack());
        defend.addActionListener(e -> doDefend());
        heal.addActionListener(e -> doHeal());
        detail.addActionListener(e -> showDetail());
        quit.addActionListener(e -> quitGame());
    }
    
    private void restingButtons(){
        cont = new JButton("CONTINUE");
        rest = new JButton("REST");
        quit2 = new JButton("QUIT");
        
        JButton[] restButton = { cont, rest, quit2};
        for(JButton button : restButton){
            buttonStyle(button);
        }
        
        //action listeners
        cont.addActionListener(e -> nextFight());
        rest.addActionListener(e -> playerRest());
        quit2.addActionListener(e -> quitGame());
    }
    
    private void combatLog(){
        //COMBAT LOG PANEL
        combatLog = new JTextArea(5,40);
        combatLog.setFont(combatFont);
        combatLog.setEditable(false);
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);
        combatLog.setOpaque(false);
        combatLog.setForeground(Color.WHITE);
        
        //scroll for combatLog
        JScrollPane logScrollPane = new JScrollPane(combatLog);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logScrollPane.setOpaque(false);
        logScrollPane.getViewport().setOpaque(false);
        
        //placing combatlog to bottom left
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setOpaque(false);
        logPanel.add(logScrollPane, BorderLayout.CENTER);
        
        add(logPanel, BorderLayout.SOUTH);
    }
    
    private void combatInterface(){
        Component[] components = getComponents();
        for(Component comp : components){
            if (comp instanceof JPanel && 
                ((JPanel) comp).getComponentCount() > 0 && 
                ((JPanel) comp).getComponent(0) instanceof JPanel) {
                remove(comp);
                break;
            }
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
        statPanel.add(playerStat, BorderLayout.WEST);
        statPanel.add(enemyStat, BorderLayout.EAST);
        
        //put everything in 1 panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(statPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonWrapper, BorderLayout.EAST);
        
        add(bottomPanel, BorderLayout.SOUTH);
        phase = true;
        revalidate();
        repaint();
    }
    
    private void showRestingPanel(){
        //removing existing bottom panel
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && 
                ((JPanel) comp).getComponentCount() > 0) {
                remove(comp);
                break;
            }
        }
        
        JPanel buttonPosition = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPosition.setOpaque(false);
        buttonPosition.add(cont);
        buttonPosition.add(rest);
        buttonPosition.add(quit2);
        buttonPosition.add(new JLabel());
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonPosition);
        
        add(buttonWrapper, BorderLayout.SOUTH);
        phase = false;
        revalidate();
        repaint();
    }
    
    private void doAttack(){
        if(!inCombat || currentEnemy == null){
            return;
        }
        
        int damageDealt = player.attack(currentEnemy);
        writeCombatLog(player.getName() + " attacks " + currentEnemy.getType() + " for " + damageDealt + " damage!");
        
        updateStats();
        
        if(currentEnemy.getHealth() <= 0){
            writeCombatLog(currentEnemy.getType() + " has been defeated!");
            combatEnd();
            return;
            
            //enemy attacks
            enemyAttack();
        }
    }
    
    private void doDefend(){
        if(!inCombat || currentEnemy == null){
            return;
        }
        
        if (!player.isDefending()) {
            player.defend();
            writeCombatLog(player.getName() + " braces for the attack!");
            updateStats();
            enemyAttack();
        } else {
            writeCombatLog(player.getName() + " is already defending!");
        }
    }
    
    private void doHeal(){
        if(!inCombat || currentEnemy == null){
            return;
        }
        
        if(player.getHealth() == player.getMaxHP()){
            writeCombatLog(player.getName() + " is already at full HP!");
        } else {
            int ohealth = player.getHealth();
            player.heal();
            int healing = player.getHealth() - ohealth;
            writeCombatLog(player.getName() + " heals for " + healing + " HP!");
            updateStats();
            enemyAttack();
        }
    }
    
    private void enemyAttack(){
        if (!inCombat || currentEnemy == null || currentEnemy.getHealth() <= 0){
            return;
        }
        
        int damageDealt = currentEnemy.attack(player);
        writeCombatLog(currentEnemy.getType() + " attacks " + player.getName() + " for " + damageDealt + " damage!");
        
        //end player defend
        player.defendEnd();
        updateStats();
        
        if (player.getHealth() <= 0) {
            writeCombatLog(player.getName() + " has been defeated!");
            gameOver();
        }
    }
    
    private void showDetails(){
        String details = "=== GAME DETAILS ===\n" +
                        "Stage: " + currentStage + "\n" +
                        "Enemies Defeated: " + enemiesDefeated + "\n" +
                        "Player: " + player.getName() + "\n" +
                        "Player HP: " + player.getHealth() + "/" + player.getMaxHP() + "\n" +
                        "Player Attack: " + player.getAttack() + "\n" +
                        "Player Defense: " + player.getDefense() + "\n";
        
        if (currentEnemy != null) {
            details += "Current Enemy: " + currentEnemy.getType() + "\n" +
                      "Enemy HP: " + currentEnemy.getHealth() + "\n" +
                      "Enemy Attack: " + currentEnemy.getAttack() + "\n" +
                      "Enemy Defense: " + currentEnemy.getDefense() + "\n";
        }
        
        JOptionPane.showMessageDialog(this, details, "Game Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void nextFight(){
        clearCombatLog();
        nextStage();
    }
    
    private void playerRest(){
        player.healToFull();
        writeCombatLog(player.getName() + " recovers to full HP thanks to a well rest.");
        updateStats();
        
        nextFight();
    }
    
    public void quitGame(){
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to save and quit?", 
            "Save & Quit", 
            JOptionPane.YES_NO_OPTION);
        
        if(choice == JOptionPane.YES_OPTION){
            //save game
            //encounter.saveGame();
            System.exit(0);
        }
    }
    
    private void nextStage(){
        //enemies per stage
        Map<Integer, ArrayList<EnemyType>> stageEnemies = new HashMap<>();
        stageEnemies.put(1, new ArrayList<>(List.of(EnemyType.SLIME))); //(new ArrayList<>()) allows to be mutable list
        stageEnemies.put(2, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN)));
        stageEnemies.put(3, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE)));
        stageEnemies.put(4, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY)));
        stageEnemies.put(5, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY, EnemyType.LIZARDMAN)));
        stageEnemies.put(6, new ArrayList<>(List.of(EnemyType.DEMON, EnemyType.BOSSBABY)));
    
        if(currentStage > 6){
            gameFinish();
            return;
        }
        
        
    }
    
    private void buttonStyle(JButton button){
        button.setFont(buttonFont);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setFocusPainted(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
}
