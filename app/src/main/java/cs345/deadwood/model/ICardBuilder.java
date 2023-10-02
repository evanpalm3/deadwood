package cs345.deadwood.model;

public interface ICardBuilder {

    ICardBuilder addName(String name);
    ICardBuilder addImageName(String img);
    ICardBuilder addBudget(int budget);
    ICardBuilder addSceneNumber(int sceneNumber);
    ICardBuilder addRole(IRole role);
    ICardBuilder addSceneDescription(String description);
    ICard getCard();
}
