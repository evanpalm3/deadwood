package cs345.deadwood.controller;

import cs345.deadwood.model.ICard;

import java.util.ArrayList;
import java.util.List;

public class CardSorter implements IStrategy{
    @Override
    public void sort(List<ICard> cards) {
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
}
