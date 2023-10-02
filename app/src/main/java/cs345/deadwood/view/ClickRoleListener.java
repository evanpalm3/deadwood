package cs345.deadwood.view;

import cs345.deadwood.controller.GameController;
import cs345.deadwood.model.IRole;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickRoleListener implements MouseListener {
    private GameController c;
    private IRole role;

    public ClickRoleListener(GameController c, IRole role){
        this.c=c;
        this.role=role;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        c.clicked(role);
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
}
