package cs345.deadwood.controller;

import cs345.deadwood.model.ICard;

import java.util.Collections;
import java.util.List;

public class CardRandom implements IStrategy{
    @Override
    public void sort(List<ICard> cards) {
        Collections.shuffle(cards);
    }
}
