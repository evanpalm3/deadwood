package cs345.deadwood.view;

import cs345.deadwood.controller.GameController;
import cs345.deadwood.model.ISet;

import javax.swing.*;

public class SetTrailerView {

    private final JFrame board;
    private GameController controller;
    private ISet set;

    public SetTrailerView(JFrame parentContainer, ISet set, GameController controller) {
        this.controller=controller;
        board = parentContainer;
        this.set=set;
    }

    public void drawSet(){
        JLabel box = new JLabel();
        box.setLocation(set.getArea().getX(), set.getArea().getY());
        box.setSize(set.getArea().getW(), set.getArea().getH());
        box.addMouseListener(new clickButtonListener(this::clicked));
        board.add(box);
    }

    private int clicked(){
        GameLog.logg("clicked trailer");
        controller.clicked(set);
        return 0;
    }
}
