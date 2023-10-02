package cs345.deadwood.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;

public class ButtonListener implements ActionListener {
    private Callable f;
    public ButtonListener(Callable function){
        super();
        f=function;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            f.call();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
