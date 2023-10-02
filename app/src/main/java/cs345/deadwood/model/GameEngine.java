package cs345.deadwood.model;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final int numberOfPlayers;
    private final List<ISet> setList;
    private final List<ICard> cardList;
    private List<Player> players;

    public GameEngine(int numberOfPlayers, List<ISet> setList, List<ICard> cardList) {
        this.numberOfPlayers = numberOfPlayers;
        this.setList = setList;
        this.cardList = cardList;
        players = new ArrayList<Player>();
        for(int i=0; i<numberOfPlayers; i++){
            players.add(new Player());
        }
    }

    public List<Player> getPlayers(){
        return players;
    }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public List<ISet> getSetList(){
        return setList;
    }

    public List<ICard> getCardList(){
        return cardList;
    }
}
