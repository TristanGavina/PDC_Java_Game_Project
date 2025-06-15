/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.CombatManager;
import com.mycompany.java_game_project.Database;
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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

/**
 * This class is gui for both combat/encounter and resting
 *
 * @author trist
 */
public final class CombatGUI extends JPanel implements CombatListeners, UpdateUI {

    private final StageManager stageManager;
    private final CombatManager combatManager;
    private final ICalculatePoint calcPoint;
    private final StyleCombatUI styleUI;
    private final Database database;
    private final int currentGameId;

    //UI stuff
    private final Image background;
    private final JTextArea combatLog;
    private final JLabel stageCount;
    private final StatsPanel playerStatPanel, enemyStatPanel;

    //combat button
    private final JButton attack, defend, heal, detail, quit;

    //rest button
    private final JButton cont, rest, quit2;

    private JPanel currentBottomPanel;
    private final Player player;
    private final Java_Game_Project frame;

    public CombatGUI(Java_Game_Project frame, Player player, Encounter encounter) {
        this.frame = frame;
        this.player = player;
        this.stageManager = new StageManager();
        this.combatManager = new CombatManager(player);
        this.calcPoint = new EnemyPointCalc();
        this.styleUI = new StyleCombatUI();

        //database connection
        this.database = new Database();
        this.database.dbsetup();

        //start db for new game
        this.currentGameId = this.database.startGame(player.getName());

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

    public void initUI() {
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

    private void eventListeners() {
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
    private JLabel makeStageLabel() {
        JLabel stageLabel = new JLabel("Current stage: STAGE " + stageManager.getCurrentStage());
        stageLabel.setForeground(Color.WHITE);
        stageLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        stageLabel.setBorder(border);
        return stageLabel;
    }

    private void combatInterface() {
        if (currentBottomPanel != null) {
            remove(currentBottomPanel);
        }

        //button panel
        JPanel buttonPosition = new JPanel(new GridLayout(3, 2, 5, 5));
        buttonPosition.setOpaque(false);
        buttonPosition.add(attack);
        buttonPosition.add(defend);
        buttonPosition.add(heal);
        buttonPosition.add(detail);
        buttonPosition.add(quit);
        buttonPosition.add(new JLabel());

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
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

    private void showRestingPanel() {
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

    private void showDetails() {
        String details = """
                         === GAME DETAILS ===
                         Stage: """ + stageManager.getCurrentStage() + "\n"
                + "Enemies Defeated: " + stageManager.getEnemiesDefeated() + "\n"
                + "Player: " + player.getName() + "\n"
                + "Player HP: " + player.getHealth() + "/" + player.getMaxHP() + "\n"
                + "Player Attack: " + player.getAttack() + "\n"
                + "Player Defense: " + player.getDefense() + "\n"
                + "Player Points: " + calcPoint.getTotalScore() + "\n"
                + "Game ID: " + currentGameId + "\n";

        JOptionPane.showMessageDialog(this, details, "Game Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nextFight() {
        clearCombatLog();

        if (stageManager.gameCompleted()) {
            gameFinish();
            return;
        }

        EnemyType nextType = stageManager.getNextEnemy();
        if (nextType == null) {
            stageManager.nextStage();
            showCombatLog(" STAGE " + stageManager.getCurrentStage() + " COMPLETED");
            showCombatLog("Moving to next stage...");
            updateDBProgress();
            nextFight();
            return;
        }

        Enemy nextEnemy = new Enemy(nextType);
        combatManager.startCombat(nextEnemy);
        showCombatLog(player.getName() + " encountered a " + nextEnemy.getType() + " !");
        updateStats();
        combatInterface();
    }

    private void playerRest() {
        player.healToFull();
        showCombatLog(player.getName() + " recovered to full HP after resting...");
        updateStats();
        nextFight();
    }

    private void updateDBProgress() {
        database.updateGame(player.getName(), calcPoint.getTotalScore(), stageManager.getCurrentStage(), stageManager.getEnemiesDefeated());
    }

    private void quitGame() {
        int choice = JOptionPane.showOptionDialog(this,
                "Quiting the game via buttons will save score. \nDo you want to...",
                "Options",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Return to Menu", "Save & Exit", "Cancel"},
                "Save & Return to Menu"
            );

        if (choice == 0) {

            //save  score in database
            database.endGame(player.getName(), calcPoint.getTotalScore(), "QUIT");
            database.userStats(player.getName());
            database.closeConnection();
            JOptionPane.showMessageDialog(this,
                    """
                    Game saved successfully!
                    Final Score: """ + calcPoint.getTotalScore() + "\n"
                    + "Thanks for playing!",
                    "Game Saved",
                    JOptionPane.INFORMATION_MESSAGE);
            returnToMain();
        } else if (choice == 1){
            database.endGame(player.getName(), calcPoint.getTotalScore(), "QUIT");
            database.userStats(player.getName());
            database.closeConnection();

            JOptionPane.showMessageDialog(this,
                "Game saved successfully!\nFinal Score: " + calcPoint.getTotalScore() + 
                "\nThanks for playing!",
                "Game Saved",
                JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        } else {
            return;
        }
    }

    private void gameFinish() {
        database.endGame(player.getName(), calcPoint.getTotalScore(), "QUIT");
        database.userStats(player.getName());
        database.closeConnection();
        
        int choice = JOptionPane.showOptionDialog(this,
                """
                    CONGRATULATIONS ON BEATING THE GAME!!!   
                Final Score: """ + calcPoint.getTotalScore() + "\n"
                + "Check console for stats!" + "\n" +
                "Would you like to...",
                "GAME COMPLETE",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Return to Menu", "Exit Game"},
                "Return to Menu"
            );
        
        if(choice == 0){
            returnToMain();
        } else {
            System.exit(0);
        }
    }
    
    @Override
    public void updateStats() {
        if (player != null) {
            playerStatPanel.playerStat(player);
        }

        if (combatManager.getCurrentEnemy() != null) {
            enemyStatPanel.enemyStat(combatManager.getCurrentEnemy());
        }

        stageCount.setText("Current stage: STAGE " + stageManager.getCurrentStage());
    }

    @Override
    public void showCombatLog(String msg) {
        SwingUtilities.invokeLater(() -> {
            combatLog.append(msg + "\n");
            combatLog.setCaretPosition(combatLog.getDocument().getLength());
        });
    }

    @Override
    public void clearCombatLog() {
        combatLog.setText("");
    }

    @Override
    public void enemyDefeated(Enemy enemy) {
        int points = calcPoint.calcPoints(enemy.getType());
        calcPoint.pointAdded(points);
        showCombatLog(enemy.getType() + " has been defeated!");
        showCombatLog(player.getName() + " earned " + points + " points! Your total score is now: " + calcPoint.getTotalScore());
        updateDBProgress();
        stageManager.nextFight();

        if (stageManager.stageCompleted()) {
            showCombatLog("STAGE " + stageManager.getCurrentStage() + " COMPLETED!");
            stageManager.nextStage();
        }

        showRestingPanel();
    }

    @Override
    public void playerDefeated() {
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
    public void stageComplete(int stage) {
        showCombatLog("STAGE  " + stage + " COMPLETE!!!");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void onAttack(int damage) {
        showCombatLog(player.getName() + " attacks for " + damage + " damage!");
        updateStats();
    }

    @Override
    public void onDefend() {
        showCombatLog(player.getName() + " takes a defensive stance!");
        updateStats();
    }

    @Override
    public void onHeal(int heal) {
        showCombatLog(player.getName() + " heals for " + heal + " HP!");
        updateStats();
    }

    @Override
    public void onEnemyAction(int damage) {
        showCombatLog(combatManager.getCurrentEnemy().getType() + " attacks for " + damage + " damage!");
        updateStats();
    }

    @Override
    public void onAction(String message) {
        showCombatLog(message);
        updateStats();
    }

    private void returnToMain() {
        SwingUtilities.invokeLater(() -> {
            try{
               Java_Game_Project newFrame = new Java_Game_Project();
                newFrame.setVisible(true);

                frame.dispose();
            } catch (Exception e) {
                System.err.println("Error returning to main menu: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
