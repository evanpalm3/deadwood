package cs345.deadwood.controller;

import cs345.deadwood.model.*;
import cs345.deadwood.view.BoardView;
import cs345.deadwood.view.GameLog;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController implements IController {
    private final GameEngine model;
    private int turn=0;
    private Player currentPlayer;
    private boolean moved=false;
    private boolean worked=false;
    private List<IRole> takenRoles;
    private List<ISet> completedSets;
    public BoardView view;
    private int day=0;

    public GameController(GameEngine model, IStrategy sorter) {
        this.model = model;
        turn = (int) (Math.random()* model.getNumberOfPlayers());
        currentPlayer=model.getPlayers().get(turn);
        takenRoles=new ArrayList<IRole>();
        completedSets=new ArrayList<ISet>();
        sorter.sort(model.getCardList());
    }

    public void clickedUpgrade(int level){
        view.setUpgradesVisible(false);
        if(!currentPlayer.getLocation().getName().equals("Office")) return;

        int[] creditC = {0,0,5,10,15,20,25};
        int[] dollarsC = {0,0,4,10,18,28,40};

        if(currentPlayer.getCredit()>=creditC[level]){
            currentPlayer.setRank(level);
            currentPlayer.setCredit(currentPlayer.getCredit()-creditC[level]);
            return;
        }
        if(currentPlayer.getCash()>=dollarsC[level]){
            currentPlayer.setRank(level);
            currentPlayer.setCash(currentPlayer.getCash()-dollarsC[level]);
            return;
        }
        GameLog.logg("not enough cash or credit");
    }

    private void sort(List<ICard> cards){
        List<ICard> temp = new ArrayList<>();
        while(cards.size()>1){
            int smallest=0;
            for(int i=1; i<cards.size(); i++){
                if(cards.get(i).getBudget()<cards.get(smallest).getBudget()){
                    smallest=i;
                }
            }
            temp.add(cards.remove(smallest));
        }
        temp.add(cards.get(0));
        while(temp.size()>0){
            cards.add(temp.remove(0));
        }
    }

    private void resetBoard(){
        takenRoles=new ArrayList<IRole>();
        completedSets=new ArrayList<ISet>();
        for(int i=0; i<model.getNumberOfPlayers(); i++){
            model.getPlayers().get(i).move(model.getSetList().get(11));
        }
        view.newCards(day);
    }

    @Override
    public void clicked(IRole role) {
        GameLog.logg("clicked "+role.getName());

        if(!currentPlayer.state.equals("takeRole")){
            GameLog.logg("must click take role button before");
            return;
        }
        if(completedSets.contains(currentPlayer.getLocation())){
            GameLog.logg("this set is already completed");
            return;
        }
        if(takenRoles.contains(role)){
            GameLog.logg("a player has already played that role");
            return;
        }
        if(!(currentPlayer.getLocation() instanceof ISetScene)) {
            GameLog.logg("role is too far away");
            return;
        }
        if(role.getLevel()>currentPlayer.getRank()){
            GameLog.logg("player level is too low");
            return;
        }

        if (view.getSetView(currentPlayer.getLocation()).card.getRoles().contains(role)) {
            takeRole(role);
            return;
        }
        SetScene set = (SetScene) currentPlayer.getLocation();
        if (set.getRoles().contains(role)) {
            takeRole(role);
            return;
        }
        GameLog.logg("role is too far away");
    }

    private void takeRole(IRole role){
        if(worked){
            GameLog.logg("Already worked this turn");
            return;
        }
        worked=true;
        GameLog.logg("player "+ currentPlayer.getPlayerNum()+" took role");
        takenRoles.add(role);
        currentPlayer.takeRole(role);
    }

    @Override
    public void clicked(ISet set) {
        if(currentPlayer.state.equals("move")&&moved==false){
            if(currentPlayer.getLocation().getNeighbors().contains(set)){
                currentPlayer.move(set);
                GameLog.logg("moved to "+set.getName());
                moved=true;
            }else{
                GameLog.logg(set.getName()+" is not next to "+currentPlayer.getLocation().getName());
            }
        }else{
            GameLog.logg("player cannot move right now");
        }
    }

    public void clickedAct(){
        if(currentPlayer.state.equals("working")){
            if(worked){
                GameLog.logg("already worked this turn");
                return;
            }
            if(!(currentPlayer.getLocation() instanceof ISetScene)) return;
            takenRoles.remove(currentPlayer.getRole());
            worked=true;
            int roll = (int)(Math.random()*6+1)+ currentPlayer.practiceChips;
            GameLog.logg("rolled a "+roll);
            currentPlayer.move(currentPlayer.getLocation());
            ICard card = view.getSetView(currentPlayer.getLocation()).card;
            if(roll>=card.getBudget()) {
                GameLog.logg("succeeded the role");
                view.removeShot((ISetScene) currentPlayer.getLocation());
                currentPlayer.state="";
                currentPlayer.practiceChips=0;

                if(card.getRoles().contains(currentPlayer.getRole())){
                    currentPlayer.setCredit(currentPlayer.getCredit()+2);
                }else{
                    currentPlayer.setCredit(currentPlayer.getCredit()+1);
                    currentPlayer.setCash(currentPlayer.getCash()+1);
                }

                if(view.getShotsLeft((ISetScene) currentPlayer.getLocation())==0){//check if scene is done
                    completedSets.add(currentPlayer.getLocation());
                    GameLog.logg(currentPlayer.getLocation().getName()+" completed");
                    if(completedSets.size()==9){//check if day is over
                        endDay();
                    }
                }
            }else{
                takenRoles.remove(currentPlayer.getRole());
                GameLog.logg("failed the role");
                if(!card.getRoles().contains(currentPlayer.getRole())){
                    currentPlayer.setCash(currentPlayer.getCash()+1);
                }
            }
        }else{
            GameLog.logg("not on a role");
        }
    }

    private void endDay(){
        day++;
        resetBoard();
        if(day==4){
            endGame();
        }
    }

    private void endGame(){
        GameLog.logg("");
        GameLog.logg("");
        int[] scores = new int[model.getNumberOfPlayers()];
        int largest=0;
        for(int i=0; i< model.getNumberOfPlayers(); i++){
            Player p = model.getPlayers().get(i);
            scores[i]=p.getCredit()+p.getCash()+5*p.getRank();
            if(scores[i]>scores[largest]) largest=i;
            GameLog.logg("Player "+p.getPlayerNum()+" with "+scores[i]+" points.");
        }
        GameLog.logg("Winner is player "+ model.getPlayers().get(largest).getPlayerNum());
    }

    public void nextTurn(){
        moved=false;
        worked=false;
        turn++;
        currentPlayer=model.getPlayers().get(turn%model.getNumberOfPlayers());
        Player.updatePlayerInfo();
    }

    public Player getPlayerTurn(){
        return currentPlayer;
    }
    public void clickedMove(){
        if(!currentPlayer.state.equals("working"))
            currentPlayer.state="move";
    }

    public void clickedTakeRole(){
        if(currentPlayer.state.equals("working")){
            GameLog.logg("player is busy working");
            return;
        }
        currentPlayer.state="takeRole";
    }

    public void clickedRehearse(){
        if(currentPlayer.state.equals("working")){
            if(worked){
                GameLog.logg("already worked this turn");
                return;
            }
            currentPlayer.practiceChips++;
            worked=true;
            GameLog.logg("number of practice chips: "+currentPlayer.practiceChips);
        }else{
            GameLog.logg("not on a role");
        }
    }

    public void clickedUpgrade(){
        if(currentPlayer.getLocation().getName().equals("Office")){
            view.setUpgradesVisible(true);
        }else{
            GameLog.logg("must be at the casting office to upgrade");
        }
    }

    @Override
    public void clickedMove(String action) {
        // TODO: Implement
    }

}
