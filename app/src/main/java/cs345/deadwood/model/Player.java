package cs345.deadwood.model;

import cs345.deadwood.view.BoardView;

import javax.swing.*;

public class Player {
    private int cash=0;
    private int credit=0;
    private ISet location;
    private static BoardView view;
    private JLabel dice;
    private static int numPlayers=0;
    private int playerNum;
    private int rank;
    public String state="";
    private IRole role;
    public int practiceChips=0;

    public Player(){
        numPlayers++;
        playerNum=numPlayers;
        rank=1;
    }

    public String getDieName(){
        char[] colors = {'b','r','p','y','w','g','y','c'};
        return "dice_"+colors[playerNum-1]+rank+".png";
    }
    public void setDice(JLabel dice){
        this.dice=dice;
    }
    public JLabel getDice(){
        return dice;
    }
    public int getPlayerNum(){
        return playerNum;
    }
    public int getCash(){
        return cash;
    }
    public int getRank(){
        return rank;
    }
    public void setRank(int rank){
        this.rank=rank;
        if(view!=null) {
            view.updatePlayerInfo();
            dice.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/" + getDieName()).getPath()));
        }
    }

    public int getCredit(){
        return credit;
    }

    public ISet getLocation(){
        return location;
    }

    public void registerView(BoardView view){
        this.view=view;
    }

    public void setCash(int cash){
        this.cash=cash;
        if(view!=null)
            view.updatePlayerInfo();
    }

    public static void updatePlayerInfo(){
        view.updatePlayerInfo();
    }

    public void setCredit(int credit){
        this.credit=credit;
        if(view!=null)
            view.updatePlayerInfo();
    }

    public void move(ISet set){
        setLocation(set);
        IArea blank = set.getBlankAreas().get(getPlayerNum()-1);
        move(blank);

        //getDice().setLocation(blank.getX(), blank.getY());
        //getDice().setSize(blank.getW(), blank.getH());
    }

    private void move(IArea area){
        getDice().setLocation(area.getX(), area.getY());
        getDice().setSize(area.getW(), area.getH());
    }

    public void takeRole(IRole role){
        this.role=role;
        state="working";
        move(role.getArea());
    }

    public IRole getRole(){
        return role;
    }

    public void setLocation(ISet location){
        this.location=location;
        if(view!=null)
            view.updatePlayerInfo();
    }
}
