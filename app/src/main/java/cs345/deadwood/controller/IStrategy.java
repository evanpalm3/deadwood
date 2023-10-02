package cs345.deadwood.controller;

import cs345.deadwood.model.ICard;

import java.util.List;

public interface IStrategy {
    public void sort(List<ICard> cards);
}
