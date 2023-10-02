package cs345.deadwood.view;

import cs345.deadwood.controller.GameController;
import cs345.deadwood.model.ISet;

import javax.swing.*;

public class SetCastingOfficeView {

    private final JFrame board;
    private GameController controller;
    private ISet set;

    public SetCastingOfficeView(JFrame parentContainer, ISet set, GameController controller) {
        board = parentContainer;
        this.controller=controller;
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
        GameLog.logg("clicked casting office");
        controller.clicked(set);
        return 0;
    }
}
