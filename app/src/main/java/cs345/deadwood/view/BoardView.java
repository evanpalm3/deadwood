package cs345.deadwood.view;

import cs345.deadwood.controller.GameController;
import cs345.deadwood.model.GameEngine;
import cs345.deadwood.model.ISet;
import cs345.deadwood.model.ISetScene;
import cs345.deadwood.model.Player;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;


public class BoardView implements MouseListener{

    private final GameController controller;
    private final GameEngine model;
    private JFrame frame;
    private final int VERTICAL_PADDING = 5;
    private final int HORIZONTAL_PADDING = 5;
    private JTextArea console;
    private JLabel[] playerMoney;
    private JLabel[] playerLocation;
    private JLabel[] playerDice;
    private JPanel[] playerInfo;
    private ArrayList<SetSceneView> setViews;
    private JButton[] upgrades = new JButton[5];

    public BoardView(GameEngine model, GameController controller) {
        this.model = model;
        this.controller = controller;
    }

    public void newCards(int day){
        for(int i=0; i<10;i++){
            setViews.get(i).setCard(model.getCardList().get(i+10*day));
        }
        replaceShots();
    }

    public void init() {
        setViews=new ArrayList<SetSceneView>();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500, 930));
        // board img is 1200 x 900. The control panel is 300 x 900, so we want the frame to be 1500 x 900
        // The top bar on the frame is about 30 pixels in height. To account for that, we increase frame height by 30, so 930.

        // Set layout to null, so we can place widgets based on x-y coordinates.
        frame.setLayout(null);

        //add dice to board
        for(int i=0; i<model.getNumberOfPlayers(); i++){
            JLabel dice = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/"+model.getPlayers().get(i).getDieName()).getPath()));
            dice.setLocation(0,0);
            dice.setSize(46,46);
            frame.add(dice);
            model.getPlayers().get(i).setDice(dice);
            model.getPlayers().get(i).move(model.getSetList().get(11));
        }

        for(ISet set : model.getSetList()){
            if (set instanceof ISetScene) {
                SetSceneView setView = new SetSceneView(frame, (ISetScene) set, controller);
                setView.drawSet();
                setViews.add(setView);
            } else if ("Trailer".equals(set.getName())) {
                SetTrailerView setView = new SetTrailerView(frame, set, controller);
                setView.drawSet();
            } else if ("Office".equals(set.getName())) {
                SetCastingOfficeView setView = new SetCastingOfficeView(frame, set, controller);
                setView.drawSet();
            } else {
                throw new RuntimeException("Found unexpected set name");
            }
        }



        URL boardImg = getClass().getClassLoader().getResource("img/board.png");
        JLabel board = new JLabel(new ImageIcon(boardImg.getPath()));
        board.setLocation(0, 0);
        board.setSize(1200, 900);
        frame.add(board);

        JPanel controlPanel = createControlPanel();
        controlPanel.setLocation(1200, 0);
        controlPanel.setSize(300, 900);
        frame.add(controlPanel);

        frame.addMouseListener(this);

        frame.pack();
        frame.setVisible(true);

        updatePlayerInfo();
        newCards(0);
    }

    public void removeShot(ISetScene set){
        for(SetSceneView view : setViews){
            if(view.getSetScene()==set){
                if(view.getShots().size()>view.shot) {
                    view.getShots().get(view.shot).setVisible(false);
                    view.shot++;
                }
            }
        }
    }

    public int getShotsLeft(ISetScene set){
        for(SetSceneView view : setViews){
            if(view.getSetScene()==set){
                return view.getShots().size()-view.shot;
            }
        }
        return -1;
    }

    public void replaceShots(){
        for(SetSceneView view : setViews){
            view.getShots().get(view.shot).setVisible(true);
            view.shot=0;
        }
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(300, 900));
        // Set height same as the board image. board image dimensions are 1200 x 900

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Add padding around edges

        JLabel team = new JLabel("Team Name");
        team.setFont(new Font("TimesRoman", Font.BOLD, 20));
        controlPanel.add(team);
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        controlPanel.add(new JSeparator());
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        JLabel playerInfoLabel = new JLabel("Players");
        playerInfoLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
        controlPanel.add(playerInfoLabel);
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        controller.view=this;
        model.getPlayers().get(0).registerView(this);
        playerMoney = new JLabel[model.getNumberOfPlayers()];
        playerLocation = new JLabel[model.getNumberOfPlayers()];
        playerDice = new JLabel[model.getNumberOfPlayers()];
        playerInfo = new JPanel[model.getNumberOfPlayers()];
        for(int i=1; i<=model.getNumberOfPlayers(); i++){
            Player current = model.getPlayers().get(i-1);
            playerInfo[i-1]=showPlayerInfo(i, current.getLocation().getName(), current.getCash(), current.getCredit(), current.getDieName());
            controlPanel.add(playerInfo[i-1]);
            controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING)));
        }

        // Show players
        /*
        controlPanel.add(showPlayerInfo(1, "Train Station", 2, 3, "dice_b1.png"));
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding
        controlPanel.add(showPlayerInfo(2, "Trailer", 2, 3, "dice_r1.png"));
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding
        controlPanel.add(showPlayerInfo(3, "Trailer", 2, 3, "dice_p1.png"));
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding
        controlPanel.add(showPlayerInfo(4, "Trailer", 2, 3, "dice_y1.png"));
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding
        controlPanel.add(showPlayerInfo(5, "Trailer", 2, 3, "dice_w1.png"));
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding
        */

        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        controlPanel.add(new JSeparator());
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        controlPanel.add(getMovePanel());
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        controlPanel.add(new JSeparator());
        controlPanel.add(Box.createRigidArea(new Dimension(0,VERTICAL_PADDING))); // Add padding

        controlPanel.add(miscInteraction());


        return controlPanel;
    }

    public void updatePlayerInfo(){
        for(int i=0; i< playerMoney.length; i++){
            Player c = model.getPlayers().get(i);
            playerMoney[i].setText("$" + c.getCash() + " C" + c.getCredit());
            playerLocation[i].setText(c.getLocation().getName());
            playerDice[i].setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/" + c.getDieName()).getPath()));
            setTurn(controller.getPlayerTurn().getPlayerNum());
        }
    }

    private JPanel showPlayerInfo(int i, String area, int cash, int credit, String dice) {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300 - HORIZONTAL_PADDING*2, 50));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        panel.add(new JLabel("Player " + i + ": "));
        panel.add(Box.createRigidArea(new Dimension(HORIZONTAL_PADDING,0))); // Add padding

        JLabel playerDice= new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/" + dice).getPath()));
        panel.add(playerDice);
        this.playerDice[i-1]=playerDice;
        panel.add(Box.createRigidArea(new Dimension(HORIZONTAL_PADDING,0))); // Add padding

        JLabel playerLocation = new JLabel(area);
        this.playerLocation[i-1]=playerLocation;
        panel.add(playerLocation);
        panel.add(Box.createRigidArea(new Dimension(HORIZONTAL_PADDING,0))); // Add padding

        JLabel money = new JLabel("$" + cash + " C" + credit); // 2 dollars and 3 credits.
        this.playerMoney[i-1]=money;
        panel.add(money);
        panel.add(Box.createRigidArea(new Dimension(HORIZONTAL_PADDING,0))); // Add padding

        return panel;
    }

    public SetSceneView getSetView(ISet set){
        for(SetSceneView s : setViews){
            if(s.getSetScene()==set) return s;
        }
        return null;
    }

    private void setTurn(int playerNum){
        for(int i=0;i<model.getNumberOfPlayers(); i++){
            playerInfo[i].setBackground(Color.white);
        }
        playerInfo[playerNum-1].setBackground(Color.yellow);
    }

    private int moveButton(){
        GameLog.logg("move button clicked");
        controller.clickedMove();;
        return 0;
    }

    private int takeRoleButton(){
        GameLog.logg("take button clicked");
        controller.clickedTakeRole();
        return 0;
    }

    private int actButton(){
        GameLog.logg("act button clicked");
        controller.clickedAct();
        return 0;
    }

    private int rehearseButton(){
        GameLog.logg("rehearse button clicked");
        controller.clickedRehearse();;
        return 0;
    }

    private int upgradeButton(){
        GameLog.logg("upgrade button clicked");
        controller.clickedUpgrade();
        return 0;
    }

    private int endTurnButton(){
        GameLog.logg("endTurn button clicked");
        controller.nextTurn();
        return 0;
    }

    private JPanel getMovePanel() {//top box
        JPanel movePanel = new JPanel();
        movePanel.setPreferredSize(new Dimension(300 - HORIZONTAL_PADDING*2, 200));
        movePanel.setLayout(null);

        JLabel panelTitle = new JLabel("Move options");
        panelTitle.setFont(new Font("TimesRoman", Font.BOLD, 18));
        panelTitle.setSize(panelTitle.getPreferredSize());
        panelTitle.setLocation(80,0);
        movePanel.add(panelTitle);

        JButton moveButton = new JButton();
        moveButton.setLocation(30,30);
        moveButton.setSize(100, 40);
        moveButton.setText("Move");
        moveButton.addActionListener(new ButtonListener(this::moveButton));
        movePanel.add(moveButton);

        JButton takeButton = new JButton();
        takeButton.setLocation(130,30);
        takeButton.setSize(100, 40);
        takeButton.setText("Take Role");
        takeButton.addActionListener(new ButtonListener(this::takeRoleButton));
        movePanel.add(takeButton);

        JButton actButton = new JButton();
        actButton.setLocation(30,70);
        actButton.setSize(100, 40);
        actButton.setText("Act");
        actButton.addActionListener(new ButtonListener(this::actButton));
        movePanel.add(actButton);

        JButton rehearseButton = new JButton();
        rehearseButton.setLocation(130,70);
        rehearseButton.setSize(100, 40);
        rehearseButton.setText("Rehearse");
        rehearseButton.addActionListener(new ButtonListener(this::rehearseButton));
        movePanel.add(rehearseButton);

        JButton upgradeButton = new JButton();
        upgradeButton.setLocation(30,110);
        upgradeButton.setSize(100, 40);
        upgradeButton.setText("Upgrade");
        upgradeButton.addActionListener(new ButtonListener(this::upgradeButton));
        movePanel.add(upgradeButton);

        JButton endTurnButton = new JButton();
        endTurnButton.setLocation(130,110);
        endTurnButton.setSize(100, 40);
        endTurnButton.setText("End Turn");
        endTurnButton.addActionListener(new ButtonListener(this::endTurnButton));
        movePanel.add(endTurnButton);
        /*
        JTextArea comment = new JTextArea("Player interaction space. E.g., Ask what the player wants to do, show valid moves");
        comment.setLineWrap(true);
        comment.setPreferredSize(movePanel.getPreferredSize());
        movePanel.add(comment);
        */

        JButton upgrade2 = new JButton();
        upgrade2.setLocation(10,190);
        upgrade2.setSize(45, 40);
        upgrade2.setText("2");
        upgrade2.addActionListener(new ButtonListener(this::upgrade2));
        movePanel.add(upgrade2);
        upgrades[0]=upgrade2;

        JButton upgrade3 = new JButton();
        upgrade3.setLocation(55,190);
        upgrade3.setSize(45, 40);
        upgrade3.setText("3");
        upgrade3.addActionListener(new ButtonListener(this::upgrade3));
        movePanel.add(upgrade3);
        upgrades[1]=upgrade3;

        JButton upgrade4 = new JButton();
        upgrade4.setLocation(100,190);
        upgrade4.setSize(45, 40);
        upgrade4.setText("4");
        upgrade4.addActionListener(new ButtonListener(this::upgrade4));
        movePanel.add(upgrade4);
        upgrades[2]=upgrade4;

        JButton upgrade5 = new JButton();
        upgrade5.setLocation(145,190);
        upgrade5.setSize(45, 40);
        upgrade5.setText("5");
        upgrade5.addActionListener(new ButtonListener(this::upgrade5));
        movePanel.add(upgrade5);
        upgrades[3]=upgrade5;

        JButton upgrade6 = new JButton();
        upgrade6.setLocation(190,190);
        upgrade6.setSize(45, 40);
        upgrade6.setText("6");
        upgrade6.addActionListener(new ButtonListener(this::upgrade6));
        movePanel.add(upgrade6);
        upgrades[4]=upgrade6;

        setUpgradesVisible(false);

        return movePanel;
    }

    private int upgrade2(){
        controller.clickedUpgrade(2);
        return 0;
    }
    private int upgrade3(){
        controller.clickedUpgrade(3);
        return 0;
    }
    private int upgrade4(){
        controller.clickedUpgrade(4);
        return 0;
    }
    private int upgrade5(){
        controller.clickedUpgrade(5);
        return 0;
    }
    private int upgrade6(){
        controller.clickedUpgrade(6);
        return 0;
    }

    public void setUpgradesVisible(boolean val){
        for(int i=0; i<5; i++){
            upgrades[i].setVisible(val);
        }
    }
    private JPanel miscInteraction() {//bottom box
        // free space to use for comments or any game related stuff. E.g., show rolling die or show game log.

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300 - HORIZONTAL_PADDING*2, 250));

        JLabel panelTitle = new JLabel("Console");
        panelTitle.setFont(new Font("TimesRoman", Font.BOLD, 18));
        panel.add(panelTitle);

        JTextArea comment = new JTextArea("");
        console=comment;
        GameLog.getInstance().registerConsole(this);
        GameLog.logg("");
        comment.setLineWrap(true);
        comment.setPreferredSize(panel.getPreferredSize());
        panel.add(comment);

        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // The top bar of the frame is about 30 pixels in height. So to get the x,y values on the board, subtract 30 from the y value.
        System.out.println("Mouse clicked at X = " + e.getX() + ", Y = " + (e.getY() - 30));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void log(String str) {
        console.setText(str);
    }
}
