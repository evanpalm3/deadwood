package cs345.deadwood.view;

import cs345.deadwood.controller.GameController;
import cs345.deadwood.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SetSceneView {

    private final JFrame board;
    private JLabel shotIcon;
    private JPanel cardPanel;
    private JLabel cardLabel;
    private ISetScene setScene;
    private GameController controller;
    public int shot=0;
    public List<JLabel> shots = new ArrayList<JLabel>();
    private List<JLabel> cardRoles = new ArrayList<>();
    public ICard card=null;

    public SetSceneView(JFrame parentContainer, ISet setScene, GameController controller) {
        this.setScene=(ISetScene) setScene;
        board = parentContainer;
        this.controller=controller;
    }

    public void setCard(ICard card){
        this.card=card;
        cardLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/"+card.getImageName()).getPath()));

        for(JLabel label : cardRoles){
            label.setVisible(false);
        }

        for(IRole role : card.getRoles()){
            JLabel roleLabel =new JLabel();
            roleLabel.setLocation(role.getArea().getX(), role.getArea().getY());
            roleLabel.setSize(role.getArea().getW(), role.getArea().getH());
            roleLabel.addMouseListener(new ClickRoleListener(controller, role));
            cardPanel.add(roleLabel);
            cardRoles.add(roleLabel);

            role.getArea().setX(setScene.getArea().getX()+role.getArea().getX());
            role.getArea().setY(setScene.getArea().getY()+role.getArea().getY());
        }
    }
    public ISetScene getSetScene(){
        return setScene;
    }
    public void drawSet(){
        /*
         * Create a JPanel to render things on the card.
         */
        cardPanel = new JPanel();
        cardPanel.setLocation(setScene.getArea().getX(), setScene.getArea().getY());
        cardPanel.setSize(setScene.getArea().getW(), setScene.getArea().getH()); // height and width from board.xml, set name "Train Station", area element
        cardPanel.setLayout(null);
        // set layout to null so we can render roles on the card (x-y values in roles in cards.xml). The x-y values for roles in cards.xml are relative to the card.
        cardPanel.setOpaque(false);
        board.add(cardPanel);


        cardPanel.addMouseListener(new clickButtonListener(this::clicked)); // uncomment this to list to clicks on this set

        cardLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/cardback.png").getPath()));
        cardLabel.setLocation(0, 0);
        cardLabel.setSize(205, 115); // height and width from board.xml, set name "Train Station", area element
        cardPanel.add(cardLabel);


        // sample code showing how to place player dice on a role
        // Role 1 is Crusty Prospector
        /*
        role1 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/dice_b1.png").getPath()));
        role1.setLocation(114, 227); // x,y values from board.xml, set name "Train Station", part Crusty Prospector
        role1.setSize(46, 46); // height and width from board.xml, set name "Train Station", part Crusty Prospector
        board.add(role1);
        */
        for(IRole role : setScene.getRoles()){
            JLabel roleLabel =new JLabel();
            roleLabel.setLocation(role.getArea().getX(), role.getArea().getY());
            roleLabel.setSize(role.getArea().getW(), role.getArea().getH());
            roleLabel.addMouseListener(new ClickRoleListener(controller, role));
            board.add(roleLabel);
        }


        // sample code showing how to place the shot icon on a take
        for(IArea a : setScene.getTakes()){
            shotIcon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/shot.png").getPath()));
            shots.add(shotIcon);
            shotIcon.setLocation(a.getX(), a.getY()); // x,y values from board.xml, set name "Train Station", take 1
            shotIcon.setSize(a.getW(), a.getH()); // height and width from board.xml, set name "Train Station", take 1
            board.add(shotIcon);
        }
        /*
        shotIcon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/shot.png").getPath()));
        shotIcon.setLocation(141, 11); // x,y values from board.xml, set name "Train Station", take 1
        shotIcon.setSize(47, 47); // height and width from board.xml, set name "Train Station", take 1
        board.add(shotIcon);*/
    }

    public List<JLabel> getShots(){
        return shots;
    }

    public int clicked(){
        GameLog.getInstance().log("clicked "+setScene.getName());
        controller.clicked(setScene);
        return 0;
    }
}

